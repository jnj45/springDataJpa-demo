package net.ecbank.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.ecbank.annotation.ServiceInfo;

/**
 * Controller의 requset Mapping 정보를 DB에 등록한다.
 */
@Service
@Transactional
@Slf4j
public class ServiceRegistService implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	/**
	 * 서비스 정보를 스캔 후 DB에 반영한다.
	 */
	public void registServiceInfo() {
		List<Map<String, Object>> serviceList = scanService();
		serviceList.forEach(map -> System.out.println(map));
	}
	
	/**
	 * Controller 메소드들의 어노테이션으로 서비스 정보를 스캔한다.
	 * @return
	 */
	private List<Map<String, Object>> scanService() {

		// 등록할 프로그램 정보 목록
		List<Map<String, Object>> programList = new ArrayList<Map<String, Object>>();

		// @Controller Annotation 처리된 클래스를 모두 찾는다.
		Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);

		for (final Object controller : controllers.values()) {
			Class<? extends Object> controllerClass = controller.getClass();
			String className = controllerClass.getName();

			log.debug("class name: {}", className);
			System.out.println("class name:" + className);
			
			if (StringUtils.indexOf(className, "$$") > 0) {
				className = StringUtils.substring(className, 0, StringUtils.indexOf(className, "$$"));
			}
			log.debug("class name2: {}", className);
			System.out.println("class name2:" + className);
			
			try {
				controllerClass = Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException("ClassNotFoundException");
			}

			RequestMapping reqMapAnno = controllerClass.getAnnotation(RequestMapping.class);
			String baseUrl = "";
			if (reqMapAnno != null) {
				baseUrl = reqMapAnno.value()[0];
			}

			Method[] methods = controllerClass.getMethods();
			for (Method method : methods) {
				
				RequestMapping 	requestMapping  = method.getAnnotation(RequestMapping.class);
				GetMapping 		getMapping  	= method.getAnnotation(GetMapping.class);
				PostMapping 	postMapping  	= method.getAnnotation(PostMapping.class);
				
				String[] urls = requestMapping != null ? requestMapping.value() :
									postMapping != null ? postMapping.value() : getMapping.value();
				
				if (method.getAnnotation(ServiceInfo.class) != null) {

					ServiceInfo serviceInfo 		= method.getAnnotation(ServiceInfo.class);

					//url방식으로 사용하지 않은 경우에는, path에서 url을 찾도록 수정.
					if(urls.length == 0) {
						urls = requestMapping.path();
					}

					for (String url : urls) {
						Map<String, Object> programInfoMap = new HashMap<String, Object>();
						String reqMap = baseUrl + url;
						programInfoMap.put("REQ_MAP",    reqMap);
						programInfoMap.put("PROGRM_NM",  serviceInfo.name());
						programInfoMap.put("PROGRM_DC",  StringUtils.defaultIfEmpty(serviceInfo.desc(), serviceInfo.name()));
						programInfoMap.put("GID", 		 StringUtils.defaultIfEmpty(serviceInfo.groupId(), reqMap.split("/")[1]));
						programList.add(programInfoMap);
					}
				}
			}
		}

		return programList;
	}

	
}
