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
    private int listId; //chiave esterna se il prodotto é in una lista // NOTA BENE FORSE É SBAGLIATO
    private int categoryId; // chiave esterna per la categoria

    public Product(int prodId, String name, String description, String logo, int listId, int categoryId) {
        this.prodId = prodId;
        this.name = name;
        this.description = description;
        this.logo = logo;
        this.listId = listId;
        this.categoryId = categoryId;
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

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    
    
}
