package cn.org.craftsmen.ms.assists.web.request;

import cn.org.craftsmen.ms.assists.domain.Language;

public class TranslateRequest {
	private String content;
	private Language from;
	private Language to;
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
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

}
