package by.bsu.serviceTest.entity;

public class Message {

    private String message;

    public Message() {}

    public Message(String message) {
        this.message = message;
    }

    public String getContent() {
        return message;
    }

    public void setContent(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
