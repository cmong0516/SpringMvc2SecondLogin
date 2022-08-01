package hello.login.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepositoryIF extends JpaRepository<Item,Long> {
}
