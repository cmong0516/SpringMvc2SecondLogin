package hello.login.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepositoryIF extends JpaRepository<Member,Long> {
}
