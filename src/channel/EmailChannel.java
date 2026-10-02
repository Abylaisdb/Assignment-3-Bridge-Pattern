package channel;
public class EmailChannel implements Channel {
    @Override
    public String channelName() {
        return "EMAIL";
    }

    @Override
    public String deliver(String notificationId, String recipient, String message) {
        return channelName() + " | id=" + notificationId + " | to=" + recipient
                + " | subject=Notification | body=" + message;
    }
}
