package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 12.08.2014
 * Time: 11:03
 */
@Entity
@Table(name = "locations")
public class Location {

    public static Model.Finder<Long, Message> find = new Model.Finder<>(Long.class, Message.class);

    @Id
    @Basic
    private Long ID;

    @Basic
    @Constraints.Required
    private Double latitude;

    @Basic
    @Constraints.Required
    private Double longitude;

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
