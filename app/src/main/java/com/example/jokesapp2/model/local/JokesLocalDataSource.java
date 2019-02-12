package com.example.jokesapp2.model.local;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.JokesDataSource;

import java.util.List;

import androidx.annotation.NonNull;

public class JokesLocalDataSource implements JokesDataSource {

    private static volatile JokesLocalDataSource INSTANCE;

    private JokeDAO jokeDAO;


    @Override
    public void getJoke(@NonNull String jokeId, JokesDataSource.GetJokeCallback callback) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Joke joke = jokeDAO.getJokeById(jokeId);
            }
        };
    }

    @Override
    public void getJokes(@NonNull JokesDataSource.LoadJokesCallback callback) {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Joke> jokeList = jokeDAO.getJokes();

                //AppExecutor.getMainThread().execute(new Runnable() {

            }
        };
    }

    @Override
    public void saveJoke(@NonNull Joke joke) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                jokeDAO.saveJoke(joke);
            }
        };

        // AppExecutor getDiskIO .execute
    }

    @Override
    public void deleteJoke(@NonNull String jokedId) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                jokeDAO.deleteJoke(jokedId);
            }
        };

        //App Executor .execute
    }
}
