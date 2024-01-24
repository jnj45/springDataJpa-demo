package net.ecbank.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import net.ecbank.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {
	
	@Autowired MemberJpaRepository memberJpaRepository;
	
	@Test
	void testSave() {
		Member member = new Member("abc");
		Member saveMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(saveMember.getId());
		assertThat(findMember.getId()).isEqualTo(saveMember.getId());
		assertThat(findMember.getUsername()).isEqualTo(saveMember.getUsername());
		assertThat(findMember).isEqualTo(member);
		
	}

	@Test
	void testFind() {
		fail("Not yet implemented");
	}

}
