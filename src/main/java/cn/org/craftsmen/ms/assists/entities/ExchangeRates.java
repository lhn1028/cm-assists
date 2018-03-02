package cn.org.craftsmen.ms.assists.entities;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.annotation.Id;

public class ExchangeRates {
	@Id
	private String id;
	private Date updateDate;
	private String source;
	private String base;
	private Map<String, Double> rates = new HashMap<String, Double>();
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Map<String, Double> getRates() {
		return rates;
	}
	public void setRates(Map<String, Double> rates) {
		this.rates = rates;
	}
	
	@Override
	public String toString() {
		String strRates = null == this.rates ? null : this.rates.keySet().stream().map(key -> {
			return String.format("%s=%s", key, this.rates.get(key));
		}).collect(Collectors.joining(","));
		
		return "ExchangeRates [id=" + id + ", updateDate=" + updateDate + ", source=" + source + ", base=" + base
				+ ", rates={" + strRates + "}]";
	}
}
