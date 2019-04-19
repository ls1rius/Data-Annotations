package com.cm.entity;

public class Label {
    private int id;
    private int categoryId;
    private int startIndex;
    private int endIndex;


    public Label(int id, int categoryId, int startIndex, int endIndex){
        this.id = id;
        this.categoryId = categoryId;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
