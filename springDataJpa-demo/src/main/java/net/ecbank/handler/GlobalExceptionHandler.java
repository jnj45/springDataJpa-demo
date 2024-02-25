package net.ecbank.handler;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.validation.BindingResult;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * ResponseEntityExceptionHandler를 상속해서 구현하면 ResponseStatusException (ErrorResponse를 구현한 Exception)에 대해 ProblemDetail 로 에러 응답처리.
 * 그래서, 브라우저 호출 시 난 ResponseStatusException도 BasicErrorController에 의해 에러페이지로 이동되지 않고 problemDetail json으로 응답처리되어버림. 
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Spring MVC 기본 예외를 제외한 Exception 핸들링
	 * @param ex
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler({Exception.class})
	@Nullable
	public ResponseEntity<Object> handleAllException(
			Exception ex, WebRequest request) throws Exception {
		
		log.debug(">>>>>>>>>>>>>>>>>>>> handleAllException");
		
		log.debug(HttpHeaders.ACCEPT + ":" + request.getHeader(HttpHeaders.ACCEPT));
		
		HttpHeaders httpHeaders = new HttpHeaders();
//		ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
//		if (ex instanceof BizRuntimeException bizEx) {
//			problemDetail.setTitle(EcMessageUtils.getMessage("error.problemDetail.title.biz"));
//		}else {
//			problemDetail.setTitle(EcMessageUtils.getMessage("error.problemDetail.title.server"));
//		}
//		problemDetail.setDetail(ex.getLocalizedMessage());
		
		ProblemDetail problemDetail = null;
		if (! (ex instanceof ErrorResponse errorResponse)) {
			problemDetail = ProblemDetail.forStatus(HttpStatusCode.valueOf(500));
//			problemDetail.setTitle(ex.getClass().getName()); //title이 없으면 'Internal Server Error' 로 설정됨.
			problemDetail.setDetail(ex.getLocalizedMessage());
		}
		return handleExceptionInternal(ex, problemDetail, httpHeaders, HttpStatusCode.valueOf(500), request);
		
	}
	
	/**
	 * handleExceptionInternal 메소드 오버라이드
	 * -> 바인딩 예외와 관련한 메세지와 에러를 추가한다.
	 */
	@Override
	@Nullable
	protected ResponseEntity<Object> handleExceptionInternal(
			Exception ex, @Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		log.debug(">>>>>>>>>>>>>>>>>>>> handleExceptionInternal");
		
		ResponseEntity<Object> responseEntity = super.handleExceptionInternal(ex, body, headers, statusCode, request);
		
        if (responseEntity.getBody() instanceof ProblemDetail problemDetailBody) {
//            problemDetailBody.setProperty("message", ex.getMessage()); //RFC 7801: details
        	// 바인딩 에러인 경우 에러 정보를 problemDtail에 추가
            if (ex instanceof MethodArgumentNotValidException subEx) {
                BindingResult result = subEx.getBindingResult();
                problemDetailBody.setProperty("message", "Validation failed for object='" + result.getObjectName() + "'. " + "Error count: " + result.getErrorCount());
                problemDetailBody.setProperty("errors", result.getAllErrors());
                problemDetailBody.setProperty("errorCount", result.getErrorCount());
            }
        }
        
//        log.debug(HttpHeaders.ACCEPT + ":" + request.getHeader(HttpHeaders.ACCEPT));
//        if (request instanceof ServletWebRequest servletWebRequest) {
//			HttpServletResponse response = servletWebRequest.getResponse();
//			if (response != null && response.isCommitted()) {
//				if (logger.isWarnEnabled()) {
//					logger.warn("Response already committed. Ignoring: " + ex);
//				}
//				return null;
//			}
//	        if (StringUtils.contains(request.getHeader(HttpHeaders.ACCEPT), "text/html")) {
//	        	try {
//					((ServletWebRequest) request).getResponse().sendRedirect("/error");
//				} catch (IOException e) {
//					throw new IllegalStateException(e);
//				}
//				return null;
//			}
//		}
        return responseEntity;
	}
		
	/**
	 * 최종 ResponseEntity를 변경하고 싶은 경우 오버라이드
	 */
	@Override
	protected ResponseEntity<Object> createResponseEntity(
			@Nullable Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
		
		log.debug(">>>>>>>>>>>>>>>>>>>> createResponseEntity");
		
		return new ResponseEntity<>(body, headers, statusCode);
	}
}
