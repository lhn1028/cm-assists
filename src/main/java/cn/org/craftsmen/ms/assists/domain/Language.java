package cn.org.craftsmen.ms.assists.domain;

import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Language {
	private String language;
	private String country;
	
	public Language() {
		super();
	}
	
	public Language(String language, String country) {
		super();
		this.language = language;
		this.country = country;
	}
	
	public String getLanguage() {
		return language;
	}
	
	public String getCountry() {
		return country;
	}
	
	@JsonIgnore
	@JsonProperty("locale")
	public Locale getLocale() {
		return new Locale(language, country);
	}

	@Override
	public String toString() {
		return String.format("%s_%s", language.toLowerCase(), language.toUpperCase());
	}
	
}
