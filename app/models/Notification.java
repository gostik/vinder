package models;

import controllers.Constants;

/**
 * Created by user_sca on 12.10.2014.
 */
public class Notification
{
    public Notification(String to, Message message) {
        this.to = to;
        this.message = message;
    }

    public String to;
    public Message message;
    public String time_to_live = Constants.time_to_live;
}
