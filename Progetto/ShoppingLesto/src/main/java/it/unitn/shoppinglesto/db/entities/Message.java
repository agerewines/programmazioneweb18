package it.unitn.shoppinglesto.db.entities;

public class Message {
    private Integer id, userId, listId;
    private String text;
    private String createdAt;
    private ShoppingList list;
    private User user;

    public Message(Integer id, Integer userId, Integer listId, String text, String createdAt, ShoppingList list, User user) {
        this.id = id;
        this.userId = userId;
        this.listId = listId;
        this.text = text;
        this.createdAt = createdAt;
        this.list = list;
        this.user = user;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getListId() {
        return listId;
    }

    public void setListId(Integer listId) {
        this.listId = listId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public ShoppingList getList() {
        return list;
    }

    public void setList(ShoppingList list) {
        this.list = list;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
