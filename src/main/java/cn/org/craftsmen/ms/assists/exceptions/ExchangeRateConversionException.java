package cn.org.craftsmen.ms.assists.exceptions;

public class ExchangeRateConversionException extends RuntimeException {
	private static final long serialVersionUID = -2567798429955978719L;
	
	private int errorCode;

	public ExchangeRateConversionException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
