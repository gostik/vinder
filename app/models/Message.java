package models;


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

    @Id
    private Long ID;

    @Basic
    User who;

    @Basic
    User whom;

    @Basic
    Boolean like_result;

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {
        this.ID = key;
    }

    @Basic
    Invitation invitation;

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

    public Boolean getLike_result() {
        return like_result;
    }

    public void setLike_result(Boolean like_result) {
        this.like_result = like_result;
    }

    public Invitation getInvitation() {
        return invitation;
    }

    public void setInvitation(Invitation invitation) {
        this.invitation = invitation;
    }
}
