package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;
import play.utils.dao.BasicModel;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: Mikhail
 * Date: 29.06.2014
 * Time: 18:20
 */

@Entity
@SuppressWarnings("serial")
public class Simple1 extends Model implements BasicModel<Long> {

    @Id
    private Long key;

    @Basic
    @Constraints.Required
    private String name;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Simple [key=" + key + ", name=" + name + "]";
    }
}