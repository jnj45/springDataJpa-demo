package net.ecbank.config;

import java.io.IOException;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.ecbank.handler.CustomAccessDeniedHandler;
import net.ecbank.handler.CustomLoginFailHandler;
import net.ecbank.handler.CustomLoginSuccessHandler;
import net.ecbank.util.EcWebUtil;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
	
	private final String[] RESOURCE_PATTERN = {"/resources/**", "/plugin/**", "/fonts/**", "/fwk/**"};

	private final String[] PERMIT_ALL_PATTERN = {"/", "/sessionTimeout", "/accessDenied", "/error", "/login/**", "/admin/code/**", "/cmn/**", "/sample/**"};

	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private final CustomLoginFailHandler customLoginFailHandler;

	private final CustomLoginSuccessHandler customLoginSuccessHandler;
	
	//정적 리소스 처리
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations())
									.requestMatchers(RESOURCE_PATTERN);
	}
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(
				authorize -> authorize.requestMatchers(PERMIT_ALL_PATTERN).permitAll()
					.requestMatchers("/test/viewAdmin", "/test/testAdmin").hasRole("ADMIN")
////					.anyRequest().access(authorizationManager)
					.anyRequest().authenticated()
		);
		
		http.formLogin(
				form -> form.loginPage("/login").permitAll()
//							.usernameParameter("userId")				// 아이디 파라미터명 설정, default: username
						    .loginProcessingUrl("/loginAction") 		// 로그인 Form Action Url, default: /login
						    .defaultSuccessUrl("/")						// 로그인 성공 후 이동 페이지
//						    .successHandler(                            // 로그인 성공 후 핸들러
//						    		(request, response, authentication) -> {
//						    			//인증 성공 후 접속하려던 페이지로 이동하는 예제.
//						    			String redirectUrl = new HttpSessionRequestCache().getRequest(request, response).getRedirectUrl();
//						    			response.sendRedirect(StringUtils.defaultIfEmpty(redirectUrl,"/"));
//						    		}
//						     ) 	
//						    .failureHandler(customLoginFailHandler)		// 로그인 실패 후 핸들러(lambda)
		);
		
		http.logout(
				logout -> logout.permitAll()
		);
		
//		http.csrf(csrf -> csrf.ignoringRequestMatchers(CSRF_EXCEPT_PATTERN));
		http.csrf(AbstractHttpConfigurer::disable);
		
		//권한 접근 제어 ExceptionHandling
		http.exceptionHandling(
			access -> access.accessDeniedHandler(customAccessDeniedHandler) //접근제한 (lambda)
		);
		
		//세션 접근 제어 ExceptionHandling
		http.exceptionHandling(
			session -> session.authenticationEntryPoint(new AjaxAuthenticationEntryPoint("/sessionTimeout"))
		);
		
		return http.build();
	}
	
	//ajax와 페이지에 따른 session time out에 대한 처리 분기를 위해 authenticationEntryPoint Custom
	public static class AjaxAuthenticationEntryPoint extends LoginUrlAuthenticationEntryPoint {

		public AjaxAuthenticationEntryPoint(String loginFormUrl) {
			super(loginFormUrl);
		}

		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
			if (EcWebUtil.isAjaxRequest(request)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "세션이 만료되었습니다.\n다시 로그인 하십시오.");
			} else {
				super.commence(request, response, authException);
			}
		}
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
	
	  /**
	   * AuthenticationProvider에서 필요한 passwordEncoder 빈.
	   * @return
	   */
//	  @Bean 
//	  BCryptPasswordEncoder bCryptPasswordEncoder() { 
//		  return new BCryptPasswordEncoder();
//	  }
}