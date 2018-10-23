/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db.entities;

/**
 *
 * @author alessandrogerevini
 */
public class ShoppingList {
    private Integer id;
    private String name, description, image;
    private Integer categoryId; // chiave esterna per la categoria di lista
    private Integer userId; // chiave esterna per la mail dell'utente
    private User user;

    public ShoppingList() {
    }

    public ShoppingList(Integer id, String name, String description, String image, Integer categoryId, Integer userId, User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.categoryId = categoryId;
        this.userId = userId;
        this.user = user;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){ this.user = user; }
}
