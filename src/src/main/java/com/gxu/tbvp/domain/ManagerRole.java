package com.gxu.tbvp.domain;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "manager_role")
public class ManagerRole implements Serializable {
    @Column(name = "managerId")
    private Integer managerid;

    @Column(name = "roleId")
    private Integer roleid;

    /**
     * @return managerId
     */
    public Integer getManagerid() {
        return managerid;
    }

    /**
     * @param managerid
     */
    public void setManagerid(Integer managerid) {
        this.managerid = managerid;
    }

    /**
     * @return roleId
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * @param roleid
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }
}