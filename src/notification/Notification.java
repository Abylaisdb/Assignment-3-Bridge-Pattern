package notification;

import channel.Channel;

public abstract class Notification {
    private final String id;
    private final String recipient;
    private final String content;
    private Channel channel; // the bridge

    protected Notification(String id, String recipient, String content, Channel channel) {
        this.id = id;
        this.recipient = recipient;
        this.content = content;
        this.channel = channel;
    }

    public String execute() {
        return channel.deliver(id, recipient, composeMessage());
    }

    public void setImplementation(Channel channel) {
        this.channel = channel;
    }

    protected abstract String composeMessage();

    public String getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getContent() {
        return content;
    }
}
