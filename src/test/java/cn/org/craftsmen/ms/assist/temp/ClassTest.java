package cn.org.craftsmen.ms.assist.temp;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClassTest {
	
	private static final class TranslateRequest {
		private String q;
		private String from;
		private String to;
		private long appid;
		private long salt;
		private String sign;
		
		public String getQ() {
			return q;
		}
		public void setQ(String q) {
			this.q = q;
		}
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
		public long getAppid() {
			return appid;
		}
		public void setAppid(long appid) {
			this.appid = appid;
		}
		public long getSalt() {
			return salt;
		}
		public void setSalt(long salt) {
			this.salt = salt;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		
		@Override
		public String toString() {
			return "TranslateRequest [q=" + q + ", from=" + from + ", to=" + to + ", appid=" + appid + ", salt=" + salt
					+ ", sign=" + sign + "]";
		}
	}
	
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
		@JsonProperty(value="trans_result")
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
			StringBuilder sb = new StringBuilder();
			
			return "TranslateResponse [from=" + from + ", to=" + to + ", translateResult=" + translateResult + "]";
		}
	}
	
	@Test
	public void testTimestamp() {
		// 1516078800
		// 1516086000
		Date date = new Date(1516086000 * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z"); 
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
		String formattedDate = sdf.format(date);
//		System.out.println(formattedDate);
	}
	
	@Test
	public void testLocale() {
//		Locale[] availableLocales = Locale.getAvailableLocales();
//		for (Locale locale : availableLocales) {
//			System.out.println(String.format("%s_%s : %s", locale.getLanguage(), locale.getCountry(), locale.getDisplayName()));
//		}
		
//		String[] languages = Locale.getISOLanguages();
//		for (String lang : languages) {
//			System.out.println(String.format("%s - %s", lang, (new Locale(lang)).getDisplayLanguage()));
//		}
		
//		Locale locale = Locale.FRENCH;
//		System.out.println(String.format("%s_%s : %s", locale.getLanguage(), locale.getCountry(), locale.getDisplayName()));
		
		Locale locale1 = Locale.JAPAN;
		Locale locale2 = new Locale("zh", "CN");
		System.out.println(String.format("%s-%s", locale1.getLanguage(), locale1.getCountry()));
		
//		System.out.println(String.format("%s_%s : %s", locale1.getLanguage(), locale1.getCountry(), locale1.getDisplayName()));
//		System.out.println(String.format("%s_%s : %s", locale2.getLanguage(), locale2.getCountry(), locale2.getDisplayName()));
//		System.out.println(locale1.equals(locale2));
//		assertEquals(locale1, locale2);
	}
	
	private static final String URI_POST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	private static final String URI_GET = "http://api.fanyi.baidu.com/api/trans/vip/translate?q={content}&from={from}&to={to}&appid={appId}&salt={salt}&sign={sign}";
	private static final long APP_ID = 20180118000116571L;
	private static final String KEY = "54CyEybMazJ5AvUVMYuz";
	
	@Test
	public void testBaiduApiByGet() throws UnsupportedEncodingException {
		final String CONTENT = "这是一段需要翻译成英文的中文文本\n再试试";
		final String FROM = "zh";
		final String TO = "en";
		final long SALT = System.currentTimeMillis();
		
		String q = URLEncoder.encode(CONTENT, "utf-8");
		String sign = buildSign(CONTENT, SALT).toLowerCase();
		
		System.out.println(q);
		System.out.println(SALT);
		System.out.println(sign);
		
		RestTemplate rest = new RestTemplate();
//		ResponseEntity<String> res = rest.getForEntity(URI_GET, String.class, CONTENT, FROM, TO, APP_ID, SALT, sign);
//		System.out.println(res.getBody());
//		ResponseEntity<TranslateResponse> res = rest.getForEntity(URI_GET, TranslateResponse.class, CONTENT, FROM, TO, APP_ID, SALT, sign);
//		System.out.println(res.getBody());
		
		TranslateResponse res = rest.getForObject(URI_GET, TranslateResponse.class, CONTENT, FROM, TO, APP_ID, SALT, sign);
		System.out.println(res);
	}
	
	private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
	private String buildSign(String content, long salt) throws UnsupportedEncodingException {
		final String template = APP_ID + content + salt + KEY;
		System.out.println(String.format("sign: %s", template));
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			return "";
		}
		md.update(template.getBytes("utf-8"));
		return bytesToHex(md.digest());
	}
	
	private TranslateRequest buildTranslateRequest(String content, String from, String to) throws UnsupportedEncodingException {
		TranslateRequest req = new TranslateRequest();
		req.setQ(content);
		req.setFrom(from);
		req.setTo(to);
		req.setAppid(APP_ID);
		req.setSalt(System.currentTimeMillis());
		req.setSign(buildSign(content, req.getSalt()).toLowerCase());
		
		return req;
	}
	
//	@Test
//	public void testBaiduApiPost() throws UnsupportedEncodingException {
////		final String CONTENT = "这是一段需要翻译成英文的中文文本\n再试试";
//		final String CONTENT = "这是一段需要翻译成英文的中文文本";
//		final String FROM = "zh";
//		final String TO = "en";
//		
//		TranslateRequest req = buildTranslateRequest(CONTENT, FROM, TO);
//		System.out.println(req);
//		RestTemplate rest = new RestTemplate();
//		ResponseEntity<String> res = rest.postForEntity(URI_POST, req, String.class);
//		
//		System.out.println(res.getBody());
//	}
}
