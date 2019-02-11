package com.example.jokesapp2.model.remote;

import com.example.jokesapp2.model.Joke;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JokesApiService {

    /* Gets String array of the API categories */
    @GET("categories")
    Call<String[]> getCategoriesArray();

    /* Gets random joke from queried category */
    @GET("random")
    Call<Joke> getJoke(@Query("category") String categoryName);
}
