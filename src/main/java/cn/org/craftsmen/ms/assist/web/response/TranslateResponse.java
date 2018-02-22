package cn.org.craftsmen.ms.assist.web.response;

import cn.org.craftsmen.ms.assist.domain.Language;

public class TranslateResponse {
	private Language from;
	private Language to;
	private String translateResult;
	private String source;
	
	public Language getFrom() {
		return from;
	}
	public void setFrom(Language from) {
		this.from = from;
	}
	public Language getTo() {
		return to;
	}
	public void setTo(Language to) {
		this.to = to;
	}
	public String getTranslateResult() {
		return translateResult;
	}
	public void setTranslateResult(String translateResult) {
		this.translateResult = translateResult;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
}
