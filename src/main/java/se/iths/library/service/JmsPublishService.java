package se.iths.library.service;

import se.iths.library.jms.model.SystemMessage;


public interface JmsPublishService {
    String publishMessage(SystemMessage systemMessage);
}
