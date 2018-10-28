/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db.entities;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Alberto Updated by: alessandrogerevini, on 17/09
 */
public class User implements Serializable {

    private Integer id;

    private String mail, firstName, lastName, avatar;
    private String password; // Cripted
    private String uuid;
    private boolean active, admin, anonymous;

    public User() {
        this.active = false;
        this.admin = false;
        this.uuid = null;
    }

    public User(Integer id, String mail, String firstName, String lastName, String avatar, String password, String uuid) {
        super();
        this.id = id;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.password = password;
        this.active = false;
        this.admin = false;
        this.uuid = uuid;
    }

    public User(Integer id, String mail, String firstName, String lastName, String password) {
        super();
        this.id = id;
        this.mail = mail;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.active = false;
        this.admin = false;
        this.uuid = null;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getFullName(){
        return this.firstName + " " + this.lastName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isAnonymous() {
        return anonymous;
    }

    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
    }
}
