package net.ecbank.resolver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecbank.util.EcWebUtil;

@Slf4j
public class GlobalHandlerExceptionResolver implements HandlerExceptionResolver {
	
	/**
	 * 정의 필요.
	 * @param request current HTTP request
	 * @param response current HTTP response
	 * @param handler the executed handler, or {@code null} if none chosen at the
	 * time of the exception (for example, if multipart resolution failed)
	 * @param ex the exception that got thrown during handler execution
	 * @return
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		
		log.debug("GlobalHandlerExceptionResolver===================================");
		
		

		if(EcWebUtil.isAjaxRequest(request)) {
			//Ajax 요청인 경우, GlobalExceptionHandler extends ResponseEntityExceptionHandler가 처리할 수 있도록 null 리턴
			return null;
		} else {
			//페이지 이동이나 form submit인 경우, 에러페이지로 이동
			ModelAndView view = new ModelAndView("error/error");
			
			log.debug("status:"+request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
			
			HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
			
			if (ex instanceof NoResourceFoundException) {
				status = HttpStatus.NOT_FOUND;
			}
			
			view.addObject("status",  status.value());
			view.addObject("message", ex.getMessage());
			
			view.addObject("httpStatus", status);
			view.addObject("exception", ex);
		
			view.setStatus(status);

			//Web Log 기록을 위한 처리
//			String stackTrace = ExceptionUtils.getStackTrace(ex);
//			view.addObject("stackTrace", 			stackTrace);
//			view.addObject("stackTraceMessage", 	ex.getMessage());
			
			return view;
		}

	}
}
