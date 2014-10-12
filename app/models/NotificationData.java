package models;

/**
 * Created by user_sca on 12.10.2014.
 */
public class NotificationData {

    String msg;
    User user;
    String time;

    public NotificationData(String msg, User user, String time) {
        this.msg = msg;
        this.user = user;
        this.time = time;
    }
}
