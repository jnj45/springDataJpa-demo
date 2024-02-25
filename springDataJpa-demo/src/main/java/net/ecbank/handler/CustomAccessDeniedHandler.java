package net.ecbank.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.ecbank.util.EcWebUtil;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * AccessDenied Custom Handler
 *
 * @author ECBANK-hhm
 * @since 2024-01-24
 */
@Component
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request,HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		if (EcWebUtil.isAjaxRequest(request)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "접근 권한이 없습니다.");
		} else {
			response.sendRedirect("/accessDenied");
		}
    }
}

