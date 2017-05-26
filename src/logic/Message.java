package logic;

public class Message {
    private final String content;
    private final User sender;
    private final int id;

    public Message(String content, User sender, int id) {
        this.content = content;
        this.sender = sender;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public int getId() {
        return id;
    }
}
