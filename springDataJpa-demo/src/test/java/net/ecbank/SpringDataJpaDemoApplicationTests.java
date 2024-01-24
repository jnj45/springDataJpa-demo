package net.ecbank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;

@SpringBootTest
@Transactional
class SpringDataJpaDemoApplicationTests {
	
	@Autowired
	EntityManager em;
	
	@Test
	void contextLoads() {
		
		
	}
	
	@Test
	void sampleTest() {
		
	}

}
