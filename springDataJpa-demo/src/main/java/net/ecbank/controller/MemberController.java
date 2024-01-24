package net.ecbank.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import net.ecbank.dto.MemberDto;
import net.ecbank.entity.Member;
import net.ecbank.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;
	
	@GetMapping("/member/{id}")
	public String findMember(@PathVariable("id") Long id) {
		Member member = memberRepository.findById(id).orElseGet(() -> new Member("",0,null));
		return member.getUsername();
	}
	
	//도메인 클래스 컨버터
	//단순 조회 기능인 경우에만 사용
	@GetMapping("/member2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}
	
	//페이징 처리
	///members/page=0&size=2&sort=id,desc&sort=username,desc
	@GetMapping("/members")
	public Page<MemberDto> findAll(Pageable pageable) {
		return memberRepository.findAll(pageable).map(MemberDto::new);
	}
	
	
	@PostConstruct
	public void init() {
		for(int i = 0; i < 100; i++)
		memberRepository.save(new Member("member"+i, i, null));
	}
}
