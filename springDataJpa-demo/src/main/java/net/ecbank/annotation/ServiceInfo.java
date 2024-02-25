package net.ecbank.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Controller에 등록된 request mapping에 대한 추가 정보를 위해 사용.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
public @interface ServiceInfo {
	String name() default "";		// 서비스 이름
	String desc() default "";		// 서비스 설명
	String groupId()  default "";		// Group ID
}
