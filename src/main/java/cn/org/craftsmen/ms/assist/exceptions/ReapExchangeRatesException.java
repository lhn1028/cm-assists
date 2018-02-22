package cn.org.craftsmen.ms.assist.exceptions;

import org.springframework.web.client.RestClientException;

public class ReapExchangeRatesException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8584698974160414730L;

	public ReapExchangeRatesException(String message, RestClientException cause) {
		super(message, cause);
	}
}
