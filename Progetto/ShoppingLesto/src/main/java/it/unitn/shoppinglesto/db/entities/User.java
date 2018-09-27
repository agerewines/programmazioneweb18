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
 * @author Alberto
 * Updated by: alessandrogerevini, on 17/09
 */
public class User implements Serializable {
    private int id;

    private String mail, firstName, lastName, avatar;
    private String password; // Cripted

    public User() {
    }

    public User(int id, String mail, String name, String surname, String avatar, String password) {
        this.id = id;
        this.mail = mail;
        this.firstName = name;
        this.lastName = surname;
        this.avatar = avatar;
        this.password = password;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    
    
    
}
