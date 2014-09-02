package models;

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
 * Time: 20:01
 */
@Entity
@Table(name = "friendships")
public class Friendship extends Model implements BasicModel<Long> {

    public static Finder<Long, Friendship> find= new Model.Finder<Long, Friendship>(Long.class, Friendship.class);

    @Id
    @Basic
    @JsonSerialize
    @JsonDeserialize
    public Long ID;

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    User user1;
//
//    @JsonProperty("who_id")
//    public Long getWhoId(){
//        if (user1 != null){
//            return user1.getKey();
//        }
//        else {
//            return null;
//        }
//    }

    @Basic
    @Constraints.Required
    @ManyToOne(fetch = FetchType.EAGER)
    User user2;
//
//    @JsonProperty("whom_id")
//    public Long getWhomId(){
//        if (user2 != null){
//            return user2.getKey();
//        }
//        else {
//            return null;
//        }
//    }


    @Basic
    @OneToOne(fetch = FetchType.EAGER)
    public Like likeToUser1;

    @Basic
    @OneToOne(fetch = FetchType.EAGER)
    public Like likeToUser2;

    @Basic
    public boolean user1Delivered;

    @Basic
    public boolean user2Delivered;

    public boolean getUser1Delivered() {
        return user1Delivered;
    }

    public boolean getUser2Delivered() {
        return user2Delivered;
    }

    public Like getLikeToUser1() {
        return likeToUser1;
    }

    public void setLikeToUser1(Like likeToUser1) {
        this.likeToUser1 = likeToUser1;
    }


    public Like getLikeToUser2() {
        return likeToUser2;
    }

    public void setLikeToUser2(Like likeToUser2) {
        this.likeToUser2 = likeToUser2;
    }

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long aLong) {
          ID = aLong;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public void setUser1Delivered(boolean b) {

        this.user1Delivered = b;
    }

    public void setUser2Delivered(boolean b) {

        this.user2Delivered = b;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
