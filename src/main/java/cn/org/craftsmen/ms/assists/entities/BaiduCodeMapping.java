package cn.org.craftsmen.ms.assists.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "v_java_locale_baidu_mapping")
public class BaiduCodeMapping {
	@Id
	@Column(name = "locale_id")
	@JsonProperty("locale_id")
	private long id;
	private String language;
	private String country;
	@Column(name="baidu_code")
	@JsonProperty("baidu_code")
	private String baiduLanguageCode;
	
	public BaiduCodeMapping() {
	}
	
	public BaiduCodeMapping(long id, String language, String country, String baiduLanguageCode) {
		this.id = id;
		this.language = language;
		this.country = country;
		this.baiduLanguageCode = baiduLanguageCode;
	}
	public long getId() {
		return id;
	}
	public String getLanguage() {
		return language;
	}
	public String getCountry() {
		return country;
	}
	public String getBaiduLanguageCode() {
		return baiduLanguageCode;
	}
	
	@Override
	public String toString() {
		return "BaiduCodeMapping [id=" + id + ", language=" + language + ", country=" + country + ", baiduLanguageCode="
				+ baiduLanguageCode + "]";
	}
}
