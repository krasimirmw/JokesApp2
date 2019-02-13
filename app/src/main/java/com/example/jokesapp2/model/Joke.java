package com.example.jokesapp2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/* POJO for Joke Object */
@Entity(tableName = "jokes")
public class Joke {

    @SerializedName("category")
    @Expose
    @Ignore
    private List<String> category;

    @SerializedName("categoryname")
    @Expose
    @ColumnInfo(name = "categoryname")
    private String categoryName;

    @SerializedName("icon_url")
    @Ignore
    @Expose
    private String iconUrl;

    @SerializedName("id")
    @Expose
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "entryid")
    private String id;

    @SerializedName("url")
    @Expose
    @Ignore
    @ColumnInfo(name = "url")
    private String url;

    @SerializedName("value")
    @Expose
    @ColumnInfo(name = "value")
    private String value;

    private boolean isFavored = false;

    @Ignore
    public Joke(List<String> category) {
        this.category = category;
    }

    @Ignore
    public Joke(List<String> category, String iconUrl, String value) {
        this.category = category;
        this.iconUrl = iconUrl;
        this.value = value;
    }

    @Ignore
    public Joke(@NotNull String id, String value, boolean isFavored) {
        this.id = id;
        this.value = value;
        this.isFavored = isFavored;
    }

    public Joke(@NotNull String id, String categoryName, String value, boolean isFavored) {
        this.id = id;
        this.categoryName = categoryName;
        this.value = value;
        this.isFavored = isFavored;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isFavored() {
        return isFavored;
    }

    public void setFavored(boolean favored) {
        isFavored = favored;
    }
}
