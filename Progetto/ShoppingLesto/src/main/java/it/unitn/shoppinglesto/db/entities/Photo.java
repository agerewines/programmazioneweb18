package it.unitn.shoppinglesto.db.entities;

public class Photo {
    private Integer id, catId;
    private String path;

    public Photo(){ }

    public Photo(int id, String path, int catId){
        this.id = id;
        this.path = path;
        this.catId = catId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
