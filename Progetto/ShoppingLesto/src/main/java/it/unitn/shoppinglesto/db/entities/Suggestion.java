package it.unitn.shoppinglesto.db.entities;

import java.sql.Time;

public class Suggestion {
    private Integer id;
    private Integer idProd;
    private Integer idList;
    private Integer counter;
    private Integer average;
    private Time first, last;
    private boolean seen;

    public Suggestion(int id, int idProd, int idList, int counter, int average, Time first, Time last, boolean seen) {
        this.id = id;
        this.idProd = idProd;
        this.idList = idList;
        this.counter = counter;
        this.average = average;
        this.first = first;
        this.last = last;
        this.seen = seen;
    }

    public Suggestion(Integer idProd, Integer idList, Integer counter, Integer average, Time first, Time last, boolean seen) {
        this.idProd = idProd;
        this.idList = idList;
        this.counter = counter;
        this.average = average;
        this.first = first;
        this.last = last;
        this.seen = seen;
    }

    public Suggestion(int id) {
        this.id = id;
    }

    public Suggestion() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProd() {
        return idProd;
    }

    public void setIdProd(Integer idProd) {
        this.idProd = idProd;
    }

    public Integer getIdList() {
        return idList;
    }

    public void setIdList(Integer idList) {
        this.idList = idList;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public Integer getAverage() {
        return average;
    }

    public void setAverage(Integer average) {
        this.average = average;
    }

    public Time getFirst() {
        return first;
    }

    public void setFirst(Time first) {
        this.first = first;
    }

    public Time getLast() {
        return last;
    }

    public void setLast(Time last) {
        this.last = last;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}

