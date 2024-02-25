package net.ecbank.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Spring MVC, Http request/response 관련 유틸리티
 */
public class EcWebUtil {

	/**
	 * 해당 요청이 ajax요청인지 여부 판단
	 * @param request
	 * @return
	 */
	public static boolean isAjaxRequest(HttpServletRequest request) {
		if ("application/json".equals(request.getContentType())
				|| "XMLHttpRequest".equals(request.getHeader("X-Requested-With"))
				|| StringUtils.contains(request.getHeader(HttpHeaders.ACCEPT),"json")){
			return true;
		} else {
			return false;
		}
	}
}
