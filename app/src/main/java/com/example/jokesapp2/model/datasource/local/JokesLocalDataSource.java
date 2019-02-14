package com.example.jokesapp2.model.datasource.local;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;
import com.example.jokesapp2.utils.AppExecutors;

import java.util.List;

import androidx.annotation.NonNull;
/**
 * Concrete implementation of a data source as a database.
 */
public class JokesLocalDataSource implements JokesDataSource {

    private static volatile JokesLocalDataSource INSTANCE;

    private AppExecutors appExecutors;
    private JokeDAO jokeDAO;

    // Private constructor for preventing direct instantiation
    private JokesLocalDataSource(AppExecutors appExecutors, JokeDAO jokeDAO) {
        this.appExecutors = appExecutors;
        this.jokeDAO = jokeDAO;
    }

    // Singleton getInstance
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

    /* Gets the joke with requested ID. Runs on MainThread */
    @Override
    public void getJoke(@NonNull String jokeId, JokesDataSource.GetJokeCallback callback) {
        final Runnable getJokeRunnable = () -> {
            final Joke joke = jokeDAO.getJokeById(jokeId);

            appExecutors.getMainThread().execute(() -> {
                if (joke != null) {
                    callback.onJokeLoaded(joke);
                } else {
                    callback.onDataNotAvailable();
                }
            });
        };
        appExecutors.getDiskIO().execute(getJokeRunnable);
    }

    /* Gets all jokes from requested Category. Runs on MainThread */
    @Override
    public void getJokesFromCategory(String category, @NonNull JokesDataSource.LoadJokesCallback callback) {
        final Runnable getJokesFromCategoryRunnable = () -> {
            final List<Joke> jokeList = jokeDAO.getJokesFromCategory(category);

            appExecutors.getMainThread().execute(() -> {
                if (jokeList.isEmpty()) {
                    callback.onDataNotAvailable();
                } else {
                    callback.onJokesLoaded(jokeList);
                }
            });
        };
        appExecutors.getDiskIO().execute(getJokesFromCategoryRunnable);
    }

    /* Saves Joke Object to database. Runs on single diskIO thread */
    @Override
    public void saveJoke(@NonNull Joke joke) {
        Runnable saveRunnable = () -> jokeDAO.saveJoke(joke);
        appExecutors.getDiskIO().execute(saveRunnable);
    }

    /* Deletes Joke Object from database. Runs on single diskIO thread */
    @Override
    public void deleteJoke(@NonNull String jokedId) {
        Runnable deleteRunnable = () -> jokeDAO.deleteJoke(jokedId);
        appExecutors.getDiskIO().execute(deleteRunnable);
    }
}
