/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unitn.shoppinglesto.db.entities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author alessandrogerevini
 */
public class Product {
    private int id; //chiave primaria di un prodotto
    private String name, description;
    private int categoryId; // chiave esterna per la categoria
    private boolean custom;
    private List<Photo> photos;
    private Category category;
    private Double price;

    public Product(){
        this.custom = false;
    }

    public Product(int id, String name, String description, int categoryId){
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.custom = false;
    }

    public Product(int id, String name, String description, int categoryId, boolean custom) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categoryId = categoryId;
        this.custom = custom;
        this.photos = new ArrayList<>();
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isCustom() {
        return custom;
    }

    public void setCustom(boolean custom) {
        this.custom = custom;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void addPhoto(Photo p) {
        if(photos == null || photos.isEmpty())
            photos = new ArrayList<>();
        photos.add(p);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
