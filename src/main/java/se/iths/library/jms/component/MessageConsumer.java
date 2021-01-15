package se.iths.library.jms.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import se.iths.library.jms.model.SystemMessage;
import java.util.ArrayList;
import java.util.List;


@Component
public class MessageConsumer {

    public static List<SystemMessage> list = new ArrayList<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);


    @JmsListener(destination = "library_queue")
    public void messageListener(SystemMessage systemMessage) {
        LOGGER.info("Message received! {}", systemMessage);
        list.add(systemMessage);
    }
}
