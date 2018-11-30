package com.gxu.tbvp.domain;

import javax.persistence.*;
import java.io.Serializable;

public class Scenic implements Serializable {
    private Integer id;

    private String scenic;

    private String province;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return scenic
     */
    public String getScenic() {
        return scenic;
    }

    /**
     * @param scenic
     */
    public void setScenic(String scenic) {
        this.scenic = scenic;
    }

    /**
     * @return provice
     */
    public String getProvince() {
        return province;
    }

    /**
     * @param province
     */
    public void setProvnice(String province) {
        this.province = province;
    }
}