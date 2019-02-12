package com.example.jokesapp2.model;

import java.util.List;

import androidx.annotation.NonNull;

public interface JokesDataSource {

    interface GetJokeCallback {

        void onJokeLoaded(Joke joke);

        void onDataNotAvailable();
    }

    interface LoadJokesCallback {

        void onJokesLoaded(List<Joke> jokes);

        void onDataNotAvailable();
    }

    void getJoke(@NonNull String jokeId, GetJokeCallback callback);

    void getJokes(@NonNull LoadJokesCallback callback);

    void saveJoke(@NonNull Joke joke);

    void deleteJoke(@NonNull String jokedId);
}
