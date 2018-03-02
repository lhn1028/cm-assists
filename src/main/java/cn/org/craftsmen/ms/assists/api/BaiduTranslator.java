package cn.org.craftsmen.ms.assists.api;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.org.craftsmen.ms.assists.exceptions.NotSupportLocaleException;
import cn.org.craftsmen.ms.assists.exceptions.TranslateException;
import cn.org.craftsmen.ms.assists.services.LanguageCodeMapper;

@Service
public class BaiduTranslator implements Translator {

	private static final class TranslateResult {
		private String src;
		private String dst;

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getDst() {
			return dst;
		}

		public void setDst(String dst) {
			this.dst = dst;
		}

		@Override
		public String toString() {
			return "TranslateResult [src=" + src + ", dst=" + dst + "]";
		}
	}

	private static final class TranslateResponse {
		private String from;
		private String to;
		@JsonProperty(value = "trans_result")
		private List<TranslateResult> translateResult;

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}

		public String getTo() {
			return to;
		}

		public void setTo(String to) {
			this.to = to;
		}

		public List<TranslateResult> getTranslateResult() {
			return translateResult;
		}

		public void setTranslateResult(List<TranslateResult> translateResult) {
			this.translateResult = translateResult;
		}

		@Override
		public String toString() {
			return "TranslateResponse [from=" + from + ", to=" + to + ", translateResult=" + translateResult + "]";
		}
	}
	
	private static final class TranslateError {
		public static final class Data {
			@JsonProperty(value="client_ip")
			private String clientIp;

			public String getClientIp() {
				return clientIp;
			}

			public void setClientIp(String clientIp) {
				this.clientIp = clientIp;
			}
		}
		@JsonProperty(value="error_code")
		private String errorCode;
		@JsonProperty(value="error_msg")
		private String errorMessage;
		private Data data;
		
		public String getErrorCode() {
			return errorCode;
		}
		public void setErrorCode(String errorCode) {
			this.errorCode = errorCode;
		}
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		public Data getData() {
			return data;
		}
		public void setData(Data data) {
			this.data = data;
		}
	}

	private static final String URI = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={content}&from={from}&to={to}&appid={appId}&salt={salt}&sign={sign}";
	private static final long APP_ID = 20180118000116571L;
	private static final String KEY = "54CyEybMazJ5AvUVMYuz";

	private RestTemplate rest;
	private ObjectMapper objectMapper;
	private LanguageCodeMapper codeMapper;

	@Autowired
	public BaiduTranslator(LanguageCodeMapper codeMapper, ObjectMapper objectMapper, RestTemplate rest) {
		this.codeMapper = codeMapper;
		this.objectMapper = objectMapper;
		this.rest = rest;
	}

	@Override
	public Locale[] getSupportLocales() {
		return codeMapper.getSupportLocales().stream().toArray(size -> new Locale[size]);
	}

	@Override
	public String translate(String content, Locale from, Locale to) {
		if (null == content || "".equals(content.trim())) {
			throw new IllegalArgumentException("Content must not be empty");
		}
		if (null == from || null == to) {
			throw new IllegalArgumentException("The locale of `from` and `to` must not be null");
		}
		
		final long SALT = System.currentTimeMillis();
		String codeFrom = codeMapper.getLanguageCode(from);
		String codeTo = codeMapper.getLanguageCode(to);
		if (null == codeFrom || "".equals(codeFrom.trim())) {
			throw buildNotSupportLocaleException(from);
		}
		if (null == codeTo || "".equals(codeTo.trim())) {
			throw buildNotSupportLocaleException(to);
		}
		String sign = buildSign(content, SALT).toLowerCase();
		
		String result = null;
		try {
			result = rest.getForObject(URI, String.class, content, codeFrom, codeTo, APP_ID, SALT, sign);
			TranslateResponse res = objectMapper.readValue(result, TranslateResponse.class);
			
			StringBuilder sb = new StringBuilder();
			if (null != res && null != res.getTranslateResult()) {
				for (TranslateResult tr : res.getTranslateResult()) {
					sb.append(tr.dst);
				}
			}
			
			return sb.toString();
		} catch (RestClientException e) {
			throw new TranslateException(HttpStatus.BAD_GATEWAY.value(), String.format("Rest link (%s) was broken", URI), e);
		} catch (JsonParseException e) {
			throw new TranslateException(HttpStatus.BAD_GATEWAY.value(), "Parse json string from server to object error", e);
		} catch (JsonMappingException e) {
			// Server return error message
			if (null == result || "".equals(result)) {
				throw new TranslateException(HttpStatus.BAD_GATEWAY.value(), String.format("Translate by %s is return null or empty string", URI), e);
			}
			try {
				TranslateError error = objectMapper.readValue(result, TranslateError.class);
				// Response error from upstream server
				throw new TranslateException(511, String.format("%s: %s", error.errorCode, error.errorMessage), e);
			} catch (IOException e1) {
				throw new TranslateException(HttpStatus.BAD_GATEWAY.value(), e1.getMessage(), e1);
			}
		} catch (IOException e) {
			throw new TranslateException(HttpStatus.BAD_GATEWAY.value(), e.getMessage(), e);
		}
		
	}

	public static String buildSign(String content, long salt) {
		final String template = APP_ID + content + salt + KEY;

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		byte[] sign = md.digest(template.getBytes());

		return Hex.encodeHexString(sign);
	}
	
	private NotSupportLocaleException buildNotSupportLocaleException(Locale locale) {
		return new NotSupportLocaleException(String.format("Not support Locale: %s_%s", locale.getLanguage(), locale.getCountry()));
	}
}
