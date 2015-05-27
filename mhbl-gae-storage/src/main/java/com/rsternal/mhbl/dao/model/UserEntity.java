package com.rsternal.mhbl.dao.model;

import com.google.appengine.api.datastore.Key;

import javax.jdo.annotations.Extension;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.Date;

@Entity
@Extension(vendorName = "datanucleus", key = "read-write", value = "true")
@NamedQueries({
        @NamedQuery(name = "UserEntity.findAll", query = "SELECT u FROM UserEntity AS u"),
        @NamedQuery(name = "UserEntity.findByLogin", query = "SELECT u FROM UserEntity AS u WHERE u.login = :login")
})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Key identifier;

    @Column
    private String firstName;

    @Column
    private String lastName;

    private String login;

    private String email;

    @Column
    private Date createdDate;

    @Column
    private Date closedDate;

    @Column
    private boolean active;

    @Column
    private String password;

    public Key getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Key identifier) {
        this.identifier = identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getClosedDate() {
        return closedDate;
    }

    public void setClosedDate(Date closedDate) {
        this.closedDate = closedDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
