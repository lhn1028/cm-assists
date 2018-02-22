package cn.org.craftsmen.ms.assist.web.response;

public class ExchangeRateConversionResponse {
	
	public static class Request {
		private double amount;
		private String from;
		private String to;
		public double getAmount() {
			return amount;
		}
		public void setAmount(double amount) {
			this.amount = amount;
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
	}
	
	private Request reqest;
	private double result;
	public Request getReqest() {
		return reqest;
	}
	public void setReqest(Request reqest) {
		this.reqest = reqest;
	}
	public double getResult() {
		return result;
	}
	public void setResult(double result) {
		this.result = result;
	}
}
