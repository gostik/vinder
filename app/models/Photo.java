package models;

import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 07.08.2014
 * Time: 17:50
 */
@Entity
@Table(name = "photos")
public class Photo extends Model implements BasicModel<Long> {
    @Id
    private Long ID;

    @Basic
    String url;

    @ManyToOne(fetch = FetchType.LAZY,optional=true)
    @JoinTable(name = "CATALOG",
            joinColumns = @JoinColumn(name = "ID_BOOK"),
            inverseJoinColumns = @JoinColumn(name = "ID_STUDENT"))
    User user;

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {

        this.ID = key;
    }

    public String getUrl() {
        return url;
    }
}
