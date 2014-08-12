package models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 17:04
 */
@Entity
@Table(name = "messages")
public class Message extends Model implements BasicModel<Long> {

    public static Finder<Long, Message> find = new Model.Finder<>(Long.class, Message.class);

    @Id
    private Long ID;

    @Basic
    @JsonIgnore
    User who;

    @JsonProperty("who_id")
    public Long getWhoId() {
        if (who != null) {
            return who.getKey();
        } else {
            return null;
        }
    }

    @Basic
    @JsonIgnore
    User whom;

    @JsonProperty("whom_id")
    public Long getWhomId() {
        if (whom != null) {
            return whom.getKey();
        } else {
            return null;
        }
    }

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {
        this.ID = key;
    }

    @Basic
    @JsonIgnore
    Friendship friendship;

    @JsonProperty("invitation_id")
    public Long getInvitationId() {
        if (friendship != null) {
            return friendship.getKey();
        } else {
            return null;
        }
    }

    @Basic
    String message;

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

    public Friendship getFriendship() {
        return friendship;
    }

    public void setFriendship(Friendship friendship) {
        this.friendship = friendship;
    }
}
