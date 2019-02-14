package com.example.jokesapp2.model.datasource;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.annotation.NonNull;

/**
 * Main entry point for accessing jokes data.
 *
 * Only getJoke() and getJokesFromCategory() have callbacks.
 */
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

    void getJokesFromCategory(String category, @NonNull LoadJokesCallback callback);

    void saveJoke(@NonNull Joke joke);

    void deleteJoke(@NonNull String jokedId);
}
