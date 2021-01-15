package se.iths.library.jms.model;

import java.io.Serializable;

public class SystemMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    private String senderEmail;
    private String receiverEmail;
    private String msgObject;
    private String message;

    public SystemMessage() {
    }

    public SystemMessage(String senderEmail, String receiverEmail, String msgObject, String message) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.msgObject = msgObject;
        this.message = message;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
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
                "senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", msgObject='" + msgObject + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
