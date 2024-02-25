package net.ecbank.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import net.ecbank.dto.MemberDto;
import net.ecbank.dto.TestForm;
import net.ecbank.exception.BizRuntimeException;
import net.ecbank.util.EcMessageUtils;

@RestController
@RequestMapping("/test")
public class TestRestController {
	
	@GetMapping("/testGet")
	public ResponseEntity<?> testGet() {
		MemberDto member = new MemberDto(1L,"홍길동","A팀");
		
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/testAdmin")
	public ResponseEntity<?> testAdmin() {
		MemberDto member = new MemberDto(1L,"관리자","IT팀");
		
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/testPost")
	public ResponseEntity<?> testPost(@Valid TestForm testForm) {
		MemberDto member = new MemberDto(1L,"홍길동","A팀");
		
//		return ResponseEntity.ok(ResponseData.builder()
//				.responseCode(ResponseCode.SUCCESS)
//				.responseMessage("성공")
//				.data(member)
//				.build());
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/testException")
	public ResponseEntity<?> testException() throws Exception {
		MemberDto member = new MemberDto(1L,"홍길동","A팀");
		if (member.getId() == 1L) {
			throw new IllegalStateException("테스트 예외 발생");
		}
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/testRuntimeException")
	public ResponseEntity<?> testRuntimeException() throws Exception {
		MemberDto member = new MemberDto(1L,"홍길동","A팀");
		if (member.getId() == 1L) {
			throw new IllegalStateException();
		}
		return ResponseEntity.ok(member);
	}
	
	@GetMapping("/testBizRuntimeException")
	public ResponseEntity<?> testBizRuntimeException() {
		MemberDto member = new MemberDto(1L,"홍길동","A팀");
		if (member.getId() == 1L) {
			throw new BizRuntimeException(EcMessageUtils.getMessage("error.sample"));
		}
		return ResponseEntity.ok(member);
	}
}
