import channel.Channel;
import channel.EmailChannel;
import channel.PushChannel;
import channel.SmsChannel;
import notification.Notification;
import notification.Reminder;
import notification.UrgentAlert;

import java.util.function.Function;

public class Main {
    private static final String RECIPIENT = "user-42";
    private static final String CONTENT = "Submit Assignment 3 by 23:59";
    private static final String REMINDER_ID = "N-001";
    private static final String ALERT_ID = "N-002";

    private static int passed = 0;
    private static int total = 0;

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--demo")) {
            runDemo();
        } else {
            System.out.println("Usage: java -cp out Main --demo");
        }
    }

    private static void runDemo() {
        checkCombination("T1", new EmailChannel(), Main::reminder,
                "EMAIL | id=N-001 | to=user-42 | subject=Notification | body=Reminder: Submit Assignment 3 by 23:59");
        checkCombination("T2", new SmsChannel(), Main::reminder,
                "SMS | id=N-001 | to=user-42 | text=Reminder: Submit Assignment 3 by 23:59");
        checkCombination("T3", new EmailChannel(), Main::alert,
                "EMAIL | id=N-002 | to=user-42 | subject=Notification | body=URGENT: Submit Assignment 3 by 23:59");
        checkCombination("T4", new SmsChannel(), Main::alert,
                "SMS | id=N-002 | to=user-42 | text=URGENT: Submit Assignment 3 by 23:59");
        checkRuntimeSwitch();
        checkCombination("T6", new PushChannel(), Main::reminder,
                "PUSH | id=N-001 | to=user-42 | title=Notification | body=Reminder: Submit Assignment 3 by 23:59");
        checkCombination("T7", new PushChannel(), Main::alert,
                "PUSH | id=N-002 | to=user-42 | title=Notification | body=URGENT: Submit Assignment 3 by 23:59");

        System.out.println("SUMMARY: " + passed + "/" + total + " PASS");
        if (passed != total) {
            System.exit(1);
        }
    }

    private static Notification reminder(Channel channel) {
        return new Reminder(REMINDER_ID, RECIPIENT, CONTENT, channel);
    }

    private static Notification alert(Channel channel) {
        return new UrgentAlert(ALERT_ID, RECIPIENT, CONTENT, channel);
    }

    private static void checkCombination(String checkId, Channel channel,
                                         Function<Channel, Notification> factory, String expected) {
        Notification notification = factory.apply(channel);
        String actual = notification.execute();
        boolean ok = record(actual.equals(expected));
        String classes = notification.getClass().getSimpleName() + " + " + channel.getClass().getSimpleName();
        System.out.println(checkId + " " + verdict(ok) + " | " + classes + " | result=" + actual);
        if (!ok) {
            System.out.println("   expected=" + expected);
        }
    }

    private static void checkRuntimeSwitch() {
        Notification original = reminder(new EmailChannel());
        String id = original.getId();
        String recipient = original.getRecipient();
        String content = original.getContent();

        String before = original.execute();
        original.setImplementation(new SmsChannel());
        Notification afterRef = original;
        String after = afterRef.execute();

        String expectedBefore =
                "EMAIL | id=N-001 | to=user-42 | subject=Notification | body=Reminder: Submit Assignment 3 by 23:59";
        String expectedAfter = "SMS | id=N-001 | to=user-42 | text=Reminder: Submit Assignment 3 by 23:59";

        boolean sameObject = original == afterRef;
        boolean stateUnchanged = id.equals(afterRef.getId())
                && recipient.equals(afterRef.getRecipient())
                && content.equals(afterRef.getContent());
        boolean ok = record(sameObject && stateUnchanged
                && before.equals(expectedBefore) && after.equals(expectedAfter));

        System.out.println("T5 " + verdict(ok) + " | sameObject=" + sameObject + " | stateUnchanged=" + stateUnchanged);
        System.out.println("   before=" + before + " | after=" + after);
        if (!ok) {
            System.out.println("   expected before=" + expectedBefore + " | expected after=" + expectedAfter);
        }
    }

    private static boolean record(boolean ok) {
        total++;
        if (ok) {
            passed++;
        }
        return ok;
    }

    private static String verdict(boolean ok) {
        return ok ? "PASS" : "FAIL";
    }
}