package se.iths.library.zMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.view.ViewScoped;

@Component
@ViewScoped
public class HelloBean {

    @Autowired
    private MessageService messageService;

    public String getHelloMsg() {
        return messageService.getHelloMsg();
    }
}
