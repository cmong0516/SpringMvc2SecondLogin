package hello.login.domain.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
@Transactional
public class MemberRepository {

    private static long sequence = 0L;

    private final EntityManager em;

    private final MemberRepositoryIF memberRepositoryIF;

    public Member save(Member member) {
        member.setId(++sequence);
        log.info("save: member = {}", member);
        em.persist(member);
        return member;
    }

    public Optional<Member> findByLoginId(String loginId) {
        return findAll().stream().filter(m -> m.getLoginId().equals(loginId)).findFirst();
    }

    public List<Member> findAll() {
        return memberRepositoryIF.findAll();
    }
}
