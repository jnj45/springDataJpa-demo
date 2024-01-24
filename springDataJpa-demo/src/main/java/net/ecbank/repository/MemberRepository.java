package net.ecbank.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import net.ecbank.dto.MemberDto;
import net.ecbank.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {
	
	//query method 예시
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age);
	
	//JPQL을 jpa repository 메소드에 작성하는 예시. 로딩 시점에 JPQL를 파싱함. 문법오류 체크 가능.
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findMember(@Param("username") String username, @Param("age") int age);
	
	@Query("select username from Member m")
	List<String> findUsernameList();
	
	@Query("select new net.ecbank.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
	List<MemberDto> findMemberDto();
	
//	@Query("select m from Member m where m.username in :names")
//	List<Member> findByUsernames(@Param("names") Collection<String> names);
	
	//유연한 반환타입
	List<Member> findMemberByUsername(String username);
	Member findMemberOneByUsername(String username);
	Optional<Member> findMemberOptionalByUsername(String username);
	
	//페이징과 정렬
	Page<Member> findByAge(int age, Pageable pageable);
	
	//카운터 쿼리 별도 지정
	@Query(value = "select m from Member m left join m.team t where m.age = :age",
			countQuery = "select count(m) from Member m where m.age = :age ")
	Page<Member> find2ByAge(@Param("age") int age, Pageable pageable);
	
	//bulk 연산
	@Modifying(clearAutomatically = true) //bulk 연산 이후에는 영속성 컨텍스트를 초기화하는 옵션을 반드시 줄 것.
	@Query("update Member m set m.age = m.age + 1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);
	
	//패치조인. N+1문제해결
	//JPQL의 fetch join 사용
	@Query("select m from Member m left join fetch m.team")
	List<Member> findMemberFetchJoin();
	
	//EntityGraph 사용
	@Override
	@EntityGraph(attributePaths = {"team"})
	List<Member> findAll();
	
	//JPQL과 같이 사용할 수도 있음.
	@EntityGraph(attributePaths = {"team"})
	@Query("select m from Member m")
	List<Member> findMemberEntityGraph();
	
	//쿼리 메소드에도 적용할 수 있음.
	@EntityGraph(attributePaths = {"team"})
	List<Member> findEntityGraphByUsername(String username);
	
	//JPA Hint
	//운영하면서 필요한 부분만 튜닝목적으로.
	//6.0버전부터 deprecated.
//	@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
//	List<Member> findReadOnlyByUsername(String username);
	
	@Lock(LockModeType.PESSIMISTIC_WRITE)
	List<Member> findLockByUsername(String username);
}
