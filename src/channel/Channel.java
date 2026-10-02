package channel;

public interface Channel {
    String channelName();

    String deliver(String notificationId, String recipient, String message);
}