package se.iths.library.jms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.*;
import se.iths.library.jms.model.SystemMessage;

import javax.jms.Queue;


@RestController
public class PublishController {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    Queue queue;


    @PostMapping("/publishMessage")
    public ResponseEntity<String> publishMessage(@RequestBody SystemMessage systemMessage) {
        try {
            jmsTemplate.convertAndSend(queue, systemMessage);
            return new ResponseEntity<>("The message has successfully Sent!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
