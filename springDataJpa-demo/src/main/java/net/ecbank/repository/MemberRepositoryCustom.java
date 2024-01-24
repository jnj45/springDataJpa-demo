package net.ecbank.repository;

import java.util.List;

import net.ecbank.entity.Member;

/**
 * 사용자 정의 repository interface
 */
public interface MemberRepositoryCustom {
	
	List<Member> findMemberCustom();
}
