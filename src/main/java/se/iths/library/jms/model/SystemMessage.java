package se.iths.library.jms.model;

import java.io.Serializable;

public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private String emailFrom;
    private String emailTo;
    private String msgObject;
    private String message;

    public SystemMessage() {
    }

    public SystemMessage(String emailFrom, String emailTo, String msgObject, String message) {
        this.emailFrom = emailFrom;
        this.emailTo = emailTo;
        this.msgObject = msgObject;
        this.message = message;
    }

    public String getEmailFrom() {
        return emailFrom;
    }

    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getMsgObject() {
        return msgObject;
    }

    public void setMsgObject(String msgObject) {
        this.msgObject = msgObject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SystemMessage{" +
                "emailFrom='" + emailFrom + '\'' +
                ", emailTo='" + emailTo + '\'' +
                ", msgObject='" + msgObject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
