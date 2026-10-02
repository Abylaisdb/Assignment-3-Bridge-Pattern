package notification;

import channel.Channel;

public class UrgentAlert extends Notification {
    public UrgentAlert(String id, String recipient, String content, Channel channel) {
        super(id, recipient, content, channel);
    }

    @Override
    protected String composeMessage() {
        return "URGENT: " + getContent();
    }
}