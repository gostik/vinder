package models;

import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 17:50
 */
@Entity
@Table(name = "photos")
public class Photo extends Model implements BasicModel<Long> {

    public static Finder<Long, Photo> find = new Model.Finder<>(Long.class, Photo.class);

    @Id
    @Basic
    public Long ID;

    @Basic
    public String url75 = "";
    @Basic
    public String url130 = "";
    @Basic
    public String url604 = "";
    @Basic
    public String url807 = "";
    @Basic
    public String url1280 = "";
    @Basic
    public String url2560 = "";

    @ManyToOne(fetch = FetchType.EAGER)
    User user;
//
//    @OneToMany(fetch = FetchType.EAGER)
//    List<Like> likeList;


    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {
        this.ID = key;
    }

    public String getUrl75() {
        return url75;
    }

    public String getUrl130() {
        return url130;
    }

    public String getUrl604() {
        return url604;
    }

    public String getUrl807() {
        return url807;
    }

    public String getUrl1280() {
        return url1280;
    }

    public String getUrl2560() {
        return url2560;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
//
//    public List<Like> getLikeList() {
//        return likeList;
//    }
//
//    public void setLikeList(List<Like> likeList) {
//        this.likeList = likeList;
//    }
}
