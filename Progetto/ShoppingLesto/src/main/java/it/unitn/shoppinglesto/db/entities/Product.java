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
    private String name, note, logo;
    private int listId; //chiave esterna se il prodotto é in una lista // NOTA BENE FORSE É SBAGLIATO
    private int categoryId; // chiave esterna per la categoria
}
