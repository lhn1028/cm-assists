package cn.org.craftsmen.ms.assist.web.response;

public class ErrorResponse {
	private int errorCode = -1;
	private String message = "Unknown error";
	
	public ErrorResponse(int errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}
	
	public int getErrorCode() {
		return errorCode;
	}
	public String getMessage() {
		return message;
	}
}
