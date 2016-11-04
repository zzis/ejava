/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nus.edu.iss.ejava.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Snow
 */
@Entity
@Table(name = "groups")
public class Group implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @NotNull
    @Id
    @Column(name = "groupid")
    @Size(max = 32)
    private String groupId;
    
    @Column(name = "userid")
    @Size(max = 32)
    private String userId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
