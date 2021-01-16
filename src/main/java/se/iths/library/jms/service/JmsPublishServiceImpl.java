package se.iths.library.jms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import se.iths.library.jms.model.SystemMessage;
import javax.jms.Queue;

@Service
public class JmsPublishServiceImpl implements JmsPublishService {

    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    Queue queue;


    @Override
    public String publishMessage(SystemMessage systemMessage) {
        try {
            jmsTemplate.convertAndSend(queue, systemMessage);
            return "successfully Sent!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
