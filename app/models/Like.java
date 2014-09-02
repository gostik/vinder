package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 17:20
 */
@Entity
@Table(name = "likes")
public class Like extends Model implements BasicModel<Long> {


    public static Finder<Long, Like> find= new Model.Finder<Long, Like>(Long.class, Like.class);

    @Id
    @Basic
    @JsonSerialize
    @JsonDeserialize
    private Long ID;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    User who;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    User whom;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    Photo photo;

    @Basic
    Boolean result;

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public User getWho() {
        return who;
    }

    public void setWho(User who) {
        this.who = who;
    }

    public User getWhom() {
        return whom;
    }

    public void setWhom(User whom) {
        this.whom = whom;
    }

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {
        this.ID = key;
    }

    @JsonProperty("who_id")
    public Long getWhoId(){
        if (who != null){
            return who.getKey();
        }
        else {
            return null;
        }
    }

    @JsonProperty("whom_id")
    public Long getWhomId(){
        if (whom != null){
            return whom.getKey();
        }
        else {
            return null;
        }
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
