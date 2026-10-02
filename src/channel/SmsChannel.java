package channel;

public class SmsChannel implements Channel {
    @Override
    public String channelName() {
        return "SMS";
    }

    @Override
    public String deliver(String notificationId, String recipient, String message) {
        String oneLine = message.replaceAll("\\s+", " ").trim();
        return channelName() + " | id=" + notificationId + " | to=" + recipient
                + " | text=" + oneLine;
    }
}