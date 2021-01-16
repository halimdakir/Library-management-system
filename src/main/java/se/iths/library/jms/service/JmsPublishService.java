package se.iths.library.jms.service;

import se.iths.library.jms.model.SystemMessage;


public interface JmsPublishService {
    String publishMessage(SystemMessage systemMessage);
}
