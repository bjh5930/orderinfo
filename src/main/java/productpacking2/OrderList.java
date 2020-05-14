package productpacking2;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="OrderList_table")
public class OrderList {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;
        private String productInfo;


        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
        public String getProductInfo() {
            return productInfo;
        }

        public void setProductInfo(String productInfo) {
            this.productInfo = productInfo;
        }

}
