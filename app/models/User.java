package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 17:05
 */
@Entity
@Table(name = "users")
public class User extends Model implements BasicModel<Long> {
    //@JsonIgnore
    public static Finder<Long, User> find = new Model.Finder<>(Long.class, User.class);

    @Id
    private Long ID;

    @Basic
    @Constraints.Required
    private String first_name;

    @Basic
    @Constraints.Required
    private String last_name;

    @Basic
    Location location;

    @Basic
    @Constraints.Required
    private Integer sex;

    @Basic
    @Constraints.Required
    private Long uid;

    @Basic
    @Constraints.Required
    private DateTime createdDate;

    @Basic
    @Constraints.Required
    private DateTime updatedDate;

    @Basic
    @Constraints.Required
    @OneToOne
    private Settings settings;

    @Basic
    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "user")
    @JsonIgnore
    Set<Photo> photos;
//    @OneToMany
//    List<Like> likeList;
//
//    @OneToMany
//    List<Message> likeList;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public DateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(DateTime createdDate) {
        this.createdDate = createdDate;
    }

    public DateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(DateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {

        this.ID = key;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }
}
