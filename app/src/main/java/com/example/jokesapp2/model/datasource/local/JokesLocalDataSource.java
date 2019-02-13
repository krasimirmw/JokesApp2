package com.example.jokesapp2.model.datasource.local;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;
import com.example.jokesapp2.utils.AppExecutors;

import java.util.List;

import androidx.annotation.NonNull;

public class JokesLocalDataSource implements JokesDataSource {

    private static volatile JokesLocalDataSource INSTANCE;

    private AppExecutors appExecutors;
    private JokeDAO jokeDAO;

    private JokesLocalDataSource(AppExecutors appExecutors, JokeDAO jokeDAO) {
        this.appExecutors = appExecutors;
        this.jokeDAO = jokeDAO;
    }

    public static JokesLocalDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull JokeDAO jokeDAO) {
        if (INSTANCE == null) {
            synchronized (JokesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new JokesLocalDataSource(appExecutors, jokeDAO);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getJoke(@NonNull String jokeId, JokesDataSource.GetJokeCallback callback) {
        final Runnable runnable = () -> {
            final Joke joke = jokeDAO.getJokeById(jokeId);

            appExecutors.getMainThread().execute(() -> {
                if (joke != null) {
                    callback.onJokeLoaded(joke);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
    }

    @Override
    public void getJokes(@NonNull JokesDataSource.LoadJokesCallback callback) {
        final Runnable runnable = () -> {
            final List<Joke> jokeList = jokeDAO.getJokes();

            appExecutors.getMainThread().execute(() -> {
                if (jokeList.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onJokesLoaded(jokeList);
                }
            });
        };
    }

    @Override
    public void saveJoke(@NonNull Joke joke) {
        Runnable saveRunnable = () -> jokeDAO.saveJoke(joke);
        appExecutors.getDiskIO().execute(saveRunnable);
    }

    @Override
    public void deleteJoke(@NonNull String jokedId) {
        Runnable deleteRunnable = () -> jokeDAO.deleteJoke(jokedId);
        appExecutors.getDiskIO().execute(deleteRunnable);
    }
}
