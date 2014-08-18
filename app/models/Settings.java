package models;

import play.data.validation.Constraints;
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
 * Time: 17:14
 */

/*
* {\"filter_by_pro\":true,\"filter_is_pro\":true,\"sex\":2,
* \"max_age\":75,\"min_age\":18,\"range_in_km\":3000,\"hide_age\":false}",
    "id": "53c50f5c6fd43c0b00cec618"*/
@Entity
@Table(name = "settings")
public class Settings extends Model implements BasicModel<Long> {

    public static Finder<Long, Settings> find = new Model.Finder<>(Long.class, Settings.class);

    @Id
    @Basic
    private Long ID;

    @Basic
    Boolean filter_by_pro;

    @Basic
    Boolean filter_is_pro;


    @Basic
    @Constraints.Max(value = 2)
    Integer sex;

    @Basic
    @Constraints.Max(value = 100)
    Integer min_age;

    @Basic
    @Constraints.Max(value = 150)
    Integer max_age;

    @Basic
    @Constraints.Max(value = 3000)
    Integer range_in_km;

    @Basic
    Boolean hide_age;

    public Boolean getFilter_by_pro() {
        return filter_by_pro;
    }

    @Override
    public Long getKey() {
        return ID;
    }

    @Override
    public void setKey(Long key) {

        this.ID = key;
    }

    public void setFilter_by_pro(Boolean filter_by_pro) {
        this.filter_by_pro = filter_by_pro;
    }

    public Boolean getFilter_is_pro() {
        return filter_is_pro;
    }

    public void setFilter_is_pro(Boolean filter_is_pro) {
        this.filter_is_pro = filter_is_pro;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getMin_age() {
        return min_age;
    }

    public void setMin_age(Integer min_age) {
        this.min_age = min_age;
    }

    public Integer getMax_age() {
        return max_age;
    }

    public void setMax_age(Integer max_age) {
        this.max_age = max_age;
    }

    public Integer getRange_in_km() {
        return range_in_km;
    }

    public void setRange_in_km(Integer range_in_km) {
        this.range_in_km = range_in_km;
    }

    public Boolean getHide_age() {
        return hide_age;
    }

    public void setHide_age(Boolean hide_age) {
        this.hide_age = hide_age;
    }

}
