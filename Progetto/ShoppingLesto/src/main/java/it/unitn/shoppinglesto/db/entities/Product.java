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
public class Product {
    private int prodId; //chiave primaria di un prodotto
    private String name, description, logo;
    private int categoryId; // chiave esterna per la categoria
    private boolean custom;

    public Product(){
        this.custom = false;
    }

    public Product(int prodId, String name, String description, int categoryId){
        this.prodId = prodId;
        this.name = name;
        this.description = description;
        this.logo = null;
        this.categoryId = categoryId;
        this.custom = false;
    }

    public Product(int prodId, String name, String description, String logo, int categoryId, boolean custom) {
        this.prodId = prodId;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.categoryId = categoryId;
        this.custom = custom;
    }

    public int getProdId() {
        return prodId;
    }

    public void setProdId(int prodId) {
        this.prodId = prodId;
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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

}
