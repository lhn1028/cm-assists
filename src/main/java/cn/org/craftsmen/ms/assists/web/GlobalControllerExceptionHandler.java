package cn.org.craftsmen.ms.assists.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import cn.org.craftsmen.ms.assists.exceptions.BadRequestException;
import cn.org.craftsmen.ms.assists.exceptions.ExchangeRateConversionException;
import cn.org.craftsmen.ms.assists.exceptions.TranslateException;
import cn.org.craftsmen.ms.assists.web.response.ErrorResponse;

@RestController
@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BadRequestException.class})
	public ErrorResponse handleBadRequest(BadRequestException e) {
		ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		
		return error;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(ExchangeRateConversionException.class)
	public ErrorResponse handleConversionError(ExchangeRateConversionException e) {
		ErrorResponse error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
		
		return error;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(TranslateException.class)
	public ErrorResponse handleTranslateError(TranslateException e) {
		ErrorResponse er = new ErrorResponse(e.getErrorCode(), e.getMessage());
		
		return er;
	}
}
