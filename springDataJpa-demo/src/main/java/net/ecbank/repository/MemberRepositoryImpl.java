package net.ecbank.repository;

import java.util.List;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import net.ecbank.entity.Member;

/**
 * 네이밍규칙: custom하고자하는 원래 repository이름 + Impl 형태로 명명해야됨.
 */
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

	private final EntityManager em;
	
	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m")
					.getResultList();
	}

}
