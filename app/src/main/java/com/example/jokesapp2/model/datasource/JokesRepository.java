package com.example.jokesapp2.model.datasource;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.annotation.NonNull;

public class JokesRepository implements JokesDataSource {

    private static JokesRepository INSTANCE = null;

    private final JokesDataSource jokesDataSource;

    private JokesRepository(JokesDataSource jokesDataSource) {
        this.jokesDataSource = jokesDataSource;
    }

    /**
     * Returns the single instance of this class
     *
     * @param jokesDataSource the device storage data source
     * @return the (@link JokesRepository) instance
     */
    public static JokesRepository getInstance(JokesDataSource jokesDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new JokesRepository(jokesDataSource);
        }
        return INSTANCE;
    }

    /**
     * Used to destroy the current instance of the class. A new one would be build when getInstance is called
     */
    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getJoke(@NonNull String jokeId, GetJokeCallback callback) {

        jokesDataSource.getJoke(jokeId, new GetJokeCallback() {
            @Override
            public void onJokeLoaded(Joke joke) {
                callback.onJokeLoaded(joke);
            }

            @Override
            public void onDataNotAvailable() {
                // If local data not available, use remote data
            }
        });
    }

    @Override
    public void getJokes(@NonNull LoadJokesCallback callback) {
        jokesDataSource.getJokes(new LoadJokesCallback() {
            @Override
            public void onJokesLoaded(List<Joke> jokes) {
                callback.onJokesLoaded(jokes);
            }

            @Override
            public void onDataNotAvailable() {
                // Get the data from remote data source
            }
        });
    }

    @Override
    public void saveJoke(@NonNull Joke joke) {
        jokesDataSource.saveJoke(joke);
    }

    @Override
    public void deleteJoke(@NonNull String jokedId) {
        jokesDataSource.deleteJoke(jokedId);
    }
}
