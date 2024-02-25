package net.ecbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

/**
 * 비즈니즈 로직 런타임 예외
 */
public class BizRuntimeException extends ResponseStatusException {

	private static final long serialVersionUID = 4158950994596685701L;

	public BizRuntimeException(String reason) {
		super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
	}
	
	public BizRuntimeException(HttpStatusCode status, String reason, Throwable cause, String messageDetailCode,
			Object[] messageDetailArguments) {
		super(status, reason, cause, messageDetailCode, messageDetailArguments);
	}

	public BizRuntimeException(HttpStatusCode status, String reason, Throwable cause) {
		super(status, reason, cause);
	}

	public BizRuntimeException(HttpStatusCode status, String reason) {
		super(status, reason);
	}

	public BizRuntimeException(HttpStatusCode status) {
		super(status);
	}

	public BizRuntimeException(int rawStatusCode, String reason, Throwable cause) {
		super(rawStatusCode, reason, cause);
	}
	
}
