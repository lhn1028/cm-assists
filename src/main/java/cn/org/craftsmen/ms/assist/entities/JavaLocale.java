package cn.org.craftsmen.ms.assist.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JavaLocale {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private String language;
	private String country;
	private String variant;
	private String displayName;
	
	public JavaLocale() {
	}

	public JavaLocale(String language, String country, String variant, String displayName) {
		super();
		this.language = language;
		this.country = country;
		this.variant = variant;
		this.displayName = displayName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getVariant() {
		return variant;
	}

	public void setVariant(String variant) {
		this.variant = variant;
	}

	@Override
	public String toString() {
		return "JavaLocale [id=" + id + ", language=" + language + ", country=" + country + ", variant=" + variant
				+ ", displayName=" + displayName + "]";
	}
}
