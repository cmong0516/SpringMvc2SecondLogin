package hello.login.domain.item;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@AllArgsConstructor
public class ItemRepository {

//    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static
    private final EntityManager em;
    private ItemRepositoryIF itemRepositoryIF;

    public Item save(Item item) {
        item.setId(++sequence);
//        store.put(item.getId(), item);
        em.persist(item);
        return item;
    }

    public Optional<Item> findById(Long id) {
        return itemRepositoryIF.findById(id);
    }

    public List<Item> findAll() {
        return itemRepositoryIF.findAll();
    }

    public void update(Long itemId, Item updateParam) {
        Optional<Item> byId = findById(itemId);
        Item findItem = byId.get();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        itemRepositoryIF.deleteAll();
    }

}
