package models;

import controllers.Constants;
import scala.Array;

import java.util.ArrayList;

/**
 * Created by user_sca on 12.10.2014.
 */
public class Notification<T>
{
    public Notification(T message) {

        data = new Data<T>(message);
    }
    public Data<T> data;
    public String time_to_live = Constants.time_to_live;
}

