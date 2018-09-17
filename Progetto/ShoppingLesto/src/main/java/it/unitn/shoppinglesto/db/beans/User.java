/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db.beans;

import java.beans.*;
import java.io.Serializable;

/**
 *
 * @author Alberto
 * Updated by: alessandrogerevini, on 17/09
 */
public class User implements Serializable {
    
    private String mail, name, surname, avatar;
    private String password; // Cripted

    public User() {
    }

    public User(String mail, String name, String surname, String avatar, String password) {
        this.mail = mail;
        this.name = name;
        this.surname = surname;
        this.avatar = avatar;
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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
    
    
    
}
