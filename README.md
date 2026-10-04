# Assignment 3 | Bridge Pattern

- **Name:** Abylai Duisim
- **Group:** SE-2530
- **Topic:** B (Notifications)
- **Repository:** https://github.com/Abylaisdb/Assignment-3-Bridge-Pattern
- **Base commit (working I1/I2 version):** `c2440f6ac178888266e791fb63d840ecd7d0938f`

## Idea

Two dimensions vary independently: the kind of notification (Reminder, UrgentAlert) and the delivery channel (Email, SMS, Push). `Notification` holds a `Channel` reference and delegates delivery to it, so any notification works with any channel and no combination subclasses are needed.

## Role map

| Role | Class | Source path |
|---|---|---|
| Abstraction | `Notification` | `src/notification/Notification.java` |
| A1 | `Reminder` | `src/notification/Reminder.java` |
| A2 | `UrgentAlert` | `src/notification/UrgentAlert.java` |
| Implementor | `Channel` | `src/channel/Channel.java` |
| I1 | `EmailChannel` | `src/channel/EmailChannel.java` |
| I2 | `SmsChannel` | `src/channel/SmsChannel.java` |
| I3 (extension) | `PushChannel` | `src/channel/PushChannel.java` |
| Client | `Main` | `src/Main.java` |

## Where to look

- **Bridge field:** `private Channel channel` in `Notification`, set through the constructor.
- **`execute()`:** in `Notification`, calls `channel.deliver(id, recipient, composeMessage())`.
- **`setImplementation(Channel)`:** in `Notification`, replaces the bridge reference at runtime.
- **`composeMessage()`:** abstract in `Notification`, implemented in `Reminder` ("Reminder: ...") and `UrgentAlert` ("URGENT: ...").
- **T5 check:** method `checkRuntimeSwitch()` in `Main`.

## Run

From the project root:

```
javac --release 17 -encoding UTF-8 -d out "@sources.txt"
java -cp out Main --demo
```

## Expected results

```
T1 PASS | Reminder + EmailChannel | result=EMAIL | id=N-001 | to=user-42 | subject=Notification | body=Reminder: Submit Assignment 3 by 23:59
T2 PASS | Reminder + SmsChannel | result=SMS | id=N-001 | to=user-42 | text=Reminder: Submit Assignment 3 by 23:59
T3 PASS | UrgentAlert + EmailChannel | result=EMAIL | id=N-002 | to=user-42 | subject=Notification | body=URGENT: Submit Assignment 3 by 23:59
T4 PASS | UrgentAlert + SmsChannel | result=SMS | id=N-002 | to=user-42 | text=URGENT: Submit Assignment 3 by 23:59
T5 PASS | sameObject=true | stateUnchanged=true
   before=EMAIL | id=N-001 | to=user-42 | subject=Notification | body=Reminder: Submit Assignment 3 by 23:59 | after=SMS | id=N-001 | to=user-42 | text=Reminder: Submit Assignment 3 by 23:59
T6 PASS | Reminder + PushChannel | result=PUSH | id=N-001 | to=user-42 | title=Notification | body=Reminder: Submit Assignment 3 by 23:59
T7 PASS | UrgentAlert + PushChannel | result=PUSH | id=N-002 | to=user-42 | title=Notification | body=URGENT: Submit Assignment 3 by 23:59
SUMMARY: 7/7 PASS
```

## Extension

`PushChannel` (I3) was added in a separate commit after the base commit. Only the new `PushChannel.java` and `Main.java` (checks T6 and T7) changed inside `src/`. See `extension.diff`.
