package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.DateTime;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
    public static Finder<Long, User> find = new Model.Finder<Long, User>(Long.class, User.class);


    @Basic
    @Id
    @JsonSerialize
    @JsonDeserialize
    private Long ID;

    @Basic
    private String first_name;

    @Basic
    private String last_name;

    @Basic
    Location location;

    @Basic
    private Integer sex;

    @Basic
    private Long uid;

    @Basic
    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date createdDate;

    @Basic
    @JsonSerialize(using=JsonDateSerializer.class)
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date updatedDate;

    @Basic
    private Integer age;

    @Basic
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Settings settings;

    @Basic
    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JsonIgnore
    List<Photo> photos;


    @Basic
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    List<Friendship> friendships;


    @Basic
    public Boolean pro_status = false;
    @Basic
    public Boolean vip_status = false;

    public Boolean getPro_status() {
        return pro_status;
    }

    public void setPro_status(Boolean pro_status) {
        this.pro_status = pro_status;
    }

    public Boolean getVip_status() {
        return vip_status;
    }

    public void setVip_status(Boolean vip_status) {
        this.vip_status = vip_status;
    }

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public static Finder<Long, User> getFind() {
        return find;
    }

    public static void setFind(Finder<Long, User> find) {
        User.find = find;
    }

    public List<Friendship> getFriendships() {
        return friendships;
    }

    public void setFriendships(ArrayList<Friendship> friendships) {
        this.friendships = friendships;
    }
}
