package com.example.jokesapp2.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;
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
    @SerializedName("icon_url")
    @Expose
    private String iconUrl;
    @SerializedName("id")
    @Expose
    @NonNull
    @PrimaryKey
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("value")
    @Expose
    private String value;

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

    public Joke(@NotNull String id, String value) {
        this.id = id;
        this.value = value;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
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
}
