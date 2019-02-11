package com.example.jokesapp2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/* POJO for Category Object */
public class Category {

    @SerializedName("category")
    @Expose
    private String category;

    public Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
