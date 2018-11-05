package it.unitn.shoppinglesto.db.entities;

public class Photo {
    private Integer id, itemId;
    private String path;

    public Photo(){ }

    public Photo(int id, String path, int itemId){
        this.id = id;
        this.path = path;
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
