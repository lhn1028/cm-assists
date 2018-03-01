package cn.org.craftsmen.ms.assists.exceptions;

public class BadRequestException extends RuntimeException {
	private static final long serialVersionUID = -2029603736423043103L;

	private int status = 500;
	private String description = "Unknown exception";

	public BadRequestException() {
		super();
	}
	
	public BadRequestException(int status, String message, String description) {
		super(message);
		
		this.status = status;
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}
	
	
}
