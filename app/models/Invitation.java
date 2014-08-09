package models;

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
@Table(name = "invitations")
public class Invitation extends Model implements BasicModel<Long> {

    public static Finder<Long, Invitation> find= new Model.Finder<>(Long.class, Invitation.class);

    @Id
    private Long ID;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.LAZY)
    User who;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.LAZY)
    User whom;

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
}
