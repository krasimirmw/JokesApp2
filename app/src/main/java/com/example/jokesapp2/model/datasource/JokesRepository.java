package com.example.jokesapp2.model.datasource;

import com.example.jokesapp2.model.Joke;

import androidx.annotation.NonNull;

public class JokesRepository implements JokesDataSource {

    @Override
    public void getJoke(@NonNull String jokeId, GetJokeCallback callback) {

    }

    @Override
    public void getJokes(@NonNull LoadJokesCallback callback) {

    }

    @Override
    public void saveJoke(@NonNull Joke joke) {

    }

    @Override
    public void deleteJoke(@NonNull String jokedId) {

    }
}
