package net.ecbank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaAuditing
public class SpringDataJpaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaDemoApplication.class, args);
	}
	
//	@Bean
//	AuditorAware<String> auditorProvider() {
//		return () -> {
//			SecurityContext securityContext = SecurityContextHolder.getContext();
//			if (securityContext.getAuthentication() != null && securityContext.getAuthentication().getPrincipal() instanceof UserDetails) {
//	            return Optional.of(((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername());
//	        }else {
//	        	return Optional.of("SYSTEM");
//	        }
//		};
//	}
	
	
}
