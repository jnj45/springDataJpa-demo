package net.ecbank.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import net.ecbank.dto.MemberDto;
import net.ecbank.entity.Member;
import net.ecbank.entity.QMember;
import net.ecbank.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	@Autowired TeamRepository teamRepository;
	@Autowired EntityManager em;
	
	@Test
	void test() {
		Member member = new Member("abc");
		Member saveMember = memberRepository.save(member);
		
		Member findMember = memberRepository.findById(saveMember.getId()).get();
		
		assertThat(findMember.getId()).isEqualTo(saveMember.getId());
		assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
		assertThat(findMember).isEqualTo(member);
	}
	
	@Test
	void testPaging() {
		
		Team teamA = new Team("teamA");
		teamRepository.save(teamA);
		
		memberRepository.save(new Member("a", 10, teamA));
		memberRepository.save(new Member("b", 10, teamA));
		memberRepository.save(new Member("c", 10, teamA));
		memberRepository.save(new Member("d", 10, teamA));
		memberRepository.save(new Member("e", 10, teamA));
		memberRepository.save(new Member("f", 10, teamA));
		
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "username"));
		
//		Page<Member> page = memberRepository.findByyAge(10, pageRequest);
		Page<Member> page = memberRepository.find2ByAge(10, pageRequest);
		long totalElements = page.getTotalElements();
		
		//entity를 dto로 변환
		Page<MemberDto> toMap = page.map(m -> new MemberDto(m.getId(), m.getUsername(), null));
		
		List<Member> content = page.getContent();	
		for(Member m : content) {
			System.out.println("member = " + m);
		}
		System.out.println("totalElements = " + totalElements);
		
		assertThat(content.size()).isEqualTo(3);
		assertThat(page.getTotalElements()).isEqualTo(6);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
		
	}
	
	@Test
	void testCustomRepository() {
		Team teamA = new Team("teamA");
		teamRepository.save(teamA);
		
		memberRepository.save(new Member("a", 10, teamA));
		memberRepository.save(new Member("b", 10, teamA));
		
		List<Member> memberCustom = memberRepository.findMemberCustom();
		for (Member member : memberCustom) {
			System.out.println("member = " + member);
		}
	}
	
	@Test
	void testAuditiong() throws InterruptedException {
		//given
		Member member = new Member("member1", 10, null);
		memberRepository.save(member);
		
		Thread.sleep(100);
		member.setUsername("member11111");
		
		em.flush();
		em.clear();
		
		//when
		Member findMember = memberRepository.findById(member.getId()).get();
		
		//then
		System.out.println("member.createDate = " + findMember.getCreateDate());
		System.out.println("member.createBy = " + findMember.getCreateBy());
		System.out.println("member.lastModifiedDate = " + findMember.getLastModifiedDate());
		System.out.println("member.lastModifiedBy = " + findMember.getLastModifiedBy());
	}
	
	@Test
	void testQueryDsl() {
		Member member = new Member("name");
		em.persist(member);
		
		JPAQueryFactory query = new JPAQueryFactory(em);
		QMember qMember = new QMember("name");
		
		Member result = query.selectFrom(qMember).fetchOne();
		
		System.out.println(result);
	}
}
