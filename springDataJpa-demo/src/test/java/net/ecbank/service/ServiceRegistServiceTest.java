package net.ecbank.service;

import java.lang.ModuleLayer.Controller;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
import net.ecbank.annotation.ServiceInfo;
import net.ecbank.entity.Service;
import net.ecbank.repository.ServiceRepository;

@SpringBootTest
@Transactional
@Slf4j
@Commit
class ServiceRegistServiceTest implements ApplicationContextAware{
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Autowired
	private ServiceRegistService serviceInfoRegistService;
	@Autowired
	private ServiceRepository serviceRepository;
	
//	@Test
	void registService() {
		serviceInfoRegistService.registServiceInfo();
	}
	
	@Test
	void getBeanInfo() {
		String[] controllerBeanNames = applicationContext.getBeanNamesForAnnotation(org.springframework.stereotype.Controller.class);
		List<Service> serviceList = new ArrayList<Service>();
		
		for (int i = 0; i < controllerBeanNames.length; i++) {
			String beanName = controllerBeanNames[i];
			System.out.println("beanName:" + beanName);
			
			Object bean = applicationContext.getBean(beanName);
			String packageName = bean.getClass().getPackageName();
			
			System.out.println("packagName:" + packageName);
			
			if (StringUtils.startsWithIgnoreCase(packageName, "net.ecbank")) {
				Method[] declaredMethods = bean.getClass().getDeclaredMethods();
				for (Method method : declaredMethods) {
					ServiceInfo[] serviceInfos = method.getDeclaredAnnotationsByType(ServiceInfo.class);
					
					if (serviceInfos.length > 0) {
						String serviceName = serviceInfos[0].name();
						System.out.println("serviceName:" + serviceName);
						RequestMapping[] requestMappings = method.getDeclaredAnnotationsByType(RequestMapping.class);
						if (requestMappings.length > 0) {
							for(String url : requestMappings[0].value()) {
								Service service = serviceRepository.findByServiceUrl(url).orElse(new Service(url));
								service.update(serviceInfos[0].name(), serviceInfos[0].desc());
								serviceList.add(service);
							}
						}
						GetMapping[] getMappings = method.getDeclaredAnnotationsByType(GetMapping.class);
						if (getMappings.length > 0) {
							for(String url : getMappings[0].value()) {
								Service service = serviceRepository.findByServiceUrl(url).orElse(new Service(url));
								service.update(serviceInfos[0].name(), serviceInfos[0].desc());
								serviceList.add(service);
							}
						}
						PostMapping[] postMappings = method.getDeclaredAnnotationsByType(PostMapping.class);
						if (postMappings.length > 0) {
							for(String url : getMappings[0].value()) {
								Service service = serviceRepository.findByServiceUrl(url).orElse(new Service(url));
								service.update(serviceInfos[0].name(), serviceInfos[0].desc());
								serviceList.add(service);
							}
						}
					}
				}
			}
		}
		
		serviceList.forEach(s -> {
			System.out.println(s);
			serviceRepository.save(s);
		});
	}

}
