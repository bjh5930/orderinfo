package productpacking2;

import productpacking2.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class OrderListViewHandler {


    @Autowired
    private OrderListRepository orderListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPackingordered_then_CREATE_1 (@Payload Packingordered packingordered) {
        try {
            if (packingordered.isMe()) {
                // view 객체 생성
                OrderList orderList = new OrderList();
                // view 객체에 이벤트의 Value 를 set 함
                orderList.setId(packingordered.getId());
                orderList.setProductInfo(packingordered.getProductInfo());
                // view 레파지 토리에 save
                orderListRepository.save(orderList);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void whenProductPacked_then_UPDATE_1(@Payload ProductPacked productPacked) {
        try {
            if (productPacked.isMe()) {
                // view 객체 조회
                List<OrderList> orderListList = orderListRepository.findByProductInfo(productPacked.getProductInfo());
                for(OrderList orderList : orderListList){
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderList.setProductInfo(productPacked.getProductInfo());
                    // view 레파지 토리에 save
                    orderListRepository.save(orderList);
                }
                Optional<OrderList> orderListOptional = orderListRepository.findById(productPacked.getId());
                if( orderListOptional.isPresent()) {
                    OrderList orderList = orderListOptional.get();
                    // view 객체에 이벤트의 eventDirectValue 를 set 함
                    orderList.setProductInfo(productPacked.getProductInfo());
                    // view 레파지 토리에 save
                    orderListRepository.save(orderList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenPackingordered_then_DELETE_1(@Payload Packingordered packingordered) {
        try {
            if (packingordered.isMe()) {
                // view 레파지 토리에 삭제 쿼리
                orderListRepository.deleteById(packingordered.getId());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}