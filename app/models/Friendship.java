package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 20:01
 */
@Entity
@Table(name = "friendships")
public class Friendship extends Model implements BasicModel<Long> {

    public static Finder<Long, Friendship> find= new Model.Finder<>(Long.class, Friendship.class);

    @Id
    private Long ID;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.LAZY)
    User who;

    @JsonProperty("who_id")
    public Long getWhoId(){
        if (who != null){
            return who.getKey();
        }
        else {
            return null;
        }
    }

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.LAZY)
    User whom;

    @JsonProperty("whom_id")
    public Long getWhomId(){
        if (whom != null){
            return whom.getKey();
        }
        else {
            return null;
        }
    }

    @Basic
    Boolean delivered;

    @Basic
    public Like likeToMe;

    @Basic
    public Like likeToHim;

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long aLong) {
          ID = aLong;
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

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }
}
