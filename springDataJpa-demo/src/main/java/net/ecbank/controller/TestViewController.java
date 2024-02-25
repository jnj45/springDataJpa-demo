package net.ecbank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.ecbank.exception.BizRuntimeException;

@Controller
@RequestMapping("/test")
public class TestViewController {

	@GetMapping("/view1")
	public String view1() {
		return "/test/view1";
	}
	
	@GetMapping("/viewAdmin")
	public String viewAdmin() {
		return "/test/view1";
	}
	
	@PostMapping("/view2")
	public String view2() {
		return "/test/view2";
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/viewError")
	public String viewError() {
		if (true) {
			throw new BizRuntimeException("테스트 예외........");
		}
		return "/test/view2";
	}
	
	@SuppressWarnings("unused")
	@GetMapping("/viewError2")
	public String viewError2() {
		if (true) {
			throw new IllegalStateException("상태오류");
		}
		return "/test/view2";
	}
	
}
