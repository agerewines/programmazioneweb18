package it.unitn.shoppinglesto.db.entities;

public class NotificationExpire {
    private String message;
    private ShoppingList list;
    private Product product;
    private User user;
    private Suggestion suggestion;

    public NotificationExpire(String message, ShoppingList list, Product product, User user) {
        this.message = message;
        this.list = list;
        this.product = product;
        this.user = user;
    }

    public NotificationExpire() { }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ShoppingList getList() {
        return list;
    }

    public void setList(ShoppingList list) {
        this.list = list;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
