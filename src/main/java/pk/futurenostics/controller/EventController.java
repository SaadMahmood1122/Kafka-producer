package pk.futurenostics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.status.Status;
import pk.futurenostics.dto.Customer;
import pk.futurenostics.service.KafkaMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/producer-app")
public class EventController {
    @Autowired
    private KafkaMessageService service;
    
    @GetMapping("/publish/{message}")
    public ResponseEntity<?> publishMessage(@PathVariable String message){
        try{
            for(int i=0; i<=10000; i++){
                service.sendMessageToTopic(message+i);

            }
            return ResponseEntity.ok("message send successfully....");

        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
    }

    @PostMapping("/publish")
    public ResponseEntity<?> sendEvent(@RequestBody Customer customer){
        System.out.println("Called sendEvent endpoint");
            try {
                service.sendEventsToTopic(customer);
                return ResponseEntity.ok("Successfull.....");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }




    }
}
