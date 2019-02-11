package com.example.jokesapp2.model.local;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.annotation.NonNull;

public interface JokesLocalDataSource {

    interface GetJokeCallback {

        void onJokeLoaded(Joke joke);

        void onDataNotAvailable();
    }

    interface LoadJokesCallback {

        void onJokesLoaded(List<Joke> jokes);

        void onDataNotAvailable();
    }

    void getJoke(@NonNull String jokeId, @NonNull GetJokeCallback callback);

}
