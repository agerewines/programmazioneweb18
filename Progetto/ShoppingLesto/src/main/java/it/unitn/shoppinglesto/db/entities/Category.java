/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db.entities;

import java.util.List;

/**
 *
 * @author alessandrogerevini
 */
public class Category {
    private int id;
    private String name, description;
    private List<Photo> photos = null;

    public Category() {
    }

    public Category(int id, String name, String description, List<Photo> photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photos = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhoto(List<Photo> photo) {
        this.photos = photo;
    }
}
