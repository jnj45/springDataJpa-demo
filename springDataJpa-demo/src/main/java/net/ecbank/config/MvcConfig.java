package net.ecbank.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.extern.slf4j.Slf4j;
import net.ecbank.resolver.GlobalHandlerExceptionResolver;

/**
 * MVC 관련 설정
 */
@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
	
	public void addViewControllers(ViewControllerRegistry registry) {
		//테스트용 임시 페이지 url 추가
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/error").setViewName("error/error");
		
		//security error page url
		registry.addViewController("/sessionTimeout").setViewName("error/sessionTimeout");
		registry.addViewController("/accessDenied").setViewName("error/accessDenied");
	}
	/**
	 * Spring 기본 Exception Resolver 목록
	 * org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
	 * org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver
	 * org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver
	 */
	@Override
	public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		resolvers.add(0, new GlobalHandlerExceptionResolver());
	}
	
	/**
	 * 디폴트 로케일 리졸버 등록 
	 * 등록 안하면 Cannot change HTTP accept header - use a different locale resolution strategy 오류 발생
	 * @return
	 */
//	@Bean
//    LocaleResolver localeResolver() {
//        SessionLocaleResolver slr = new SessionLocaleResolver();
//        slr.setDefaultLocale(Locale.KOREA);
//        return slr;
//    }
	
	/**
	 * 파라미터로 로케일 변경 인터셉터
	 * @return
	 */
//	@Bean
//	LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		localeChangeInterceptor.setParamName("lang");
//		return localeChangeInterceptor;
//	}
	
	/**
	 * 인터셉터 추가
	 */
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(localeChangeInterceptor());
//	}
	
}
