package pk.futurenostics.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import pk.futurenostics.dto.Customer;


@Service
public class KafkaMessageService {
    @Autowired
    private KafkaTemplate template;

    public void sendMessageToTopic(String message){
        CompletableFuture<SendResult<String,Object>> future = template.send("javatech1-demo-03", message);
        future.whenComplete((result,ex)-> {
                if (ex == null) {
                    System.out.println("Send message=["+message+"] with offset=["+result.getRecordMetadata().offset()+"]");
                }else{
                    System.out.println("Unable to send message=["+message+"] due to: "+ex.getMessage());
                }
        });
    }

    public void sendEventsToTopic(Customer customer){
        CompletableFuture<SendResult<String,Object>> future = template.send("SaadTopic", customer);
        future.whenComplete((result,ex)-> {
                if (ex == null) {
                    System.out.println("Send message=["+customer.toString()+"] with offset=["+result.getRecordMetadata().offset()+"]");
                }else{
                    System.out.println("Unable to send message=["+customer.toString()+"] due to: "+ex.getMessage());
                }
        });
    }

}
