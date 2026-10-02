package notification;

import channel.Channel;

public class Reminder extends Notification {
    public Reminder(String id, String recipient, String content, Channel channel) {
        super(id, recipient, content, channel);
    }

    @Override
    protected String composeMessage() {
        return "Reminder: " + getContent();
    }
}