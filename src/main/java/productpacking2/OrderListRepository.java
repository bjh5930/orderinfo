package productpacking2;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderListRepository extends CrudRepository<OrderList, Long> {

    List<OrderList> findByProductInfo(String productInfo);

}