package se.iths.library.zMessage;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Service
public class DefaultMsgService implements MessageService {
    @Override
    public String getHelloMsg() {
        return String.format("Hi there!! The time now is %s goodbye...",
                LocalDateTime.now().format(
                        DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }
}
