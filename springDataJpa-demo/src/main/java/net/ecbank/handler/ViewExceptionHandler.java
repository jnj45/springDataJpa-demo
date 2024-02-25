package net.ecbank.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import lombok.extern.slf4j.Slf4j;

//@ControllerAdvice(annotations = Controller.class)
@Slf4j
public class ViewExceptionHandler {
	
	@ExceptionHandler({Exception.class})
	public String handleAllException(Exception ex, WebRequest request) throws Exception {
		log.debug("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		return "/error";
	}
}
