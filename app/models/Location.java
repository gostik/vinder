package models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 12.08.2014
 * Time: 11:03
 */
//@Entity
//@Table(name = "locations")
@Embeddable
public class Location {

    public static Model.Finder<Long, Message> find = new Model.Finder<Long, Message>(Long.class, Message.class);

    @Id
    @Basic
    @JsonSerialize
    @JsonDeserialize
    private Long ID;

    @Basic
    @Constraints.Required
    private String latitude;

    @Basic
    @Constraints.Required
    private String longitude;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
