package cn.org.craftsmen.ms.assist.exceptions;

public class TranslateException extends RuntimeException {

	private static final long serialVersionUID = 3616607400613737255L;
	
	private int errorCode;

	public TranslateException(int errorCode, String message, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}
}
