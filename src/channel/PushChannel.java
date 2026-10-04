package channel;

public class PushChannel implements Channel {
    @Override
    public String channelName() {
        return "PUSH";
    }

    @Override
    public String deliver(String notificationId, String recipient, String message) {
        return channelName() + " | id=" + notificationId + " | to=" + recipient
                + " | title=Notification | body=" + message;
    }
}