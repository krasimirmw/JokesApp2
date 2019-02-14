package com.example.jokesapp2.jokedetail;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;
import com.example.jokesapp2.utils.PrefUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JokePresenter implements JokeContract.Presenter, JokeContract.Interactor.OnFinishedListener {

    private JokeContract.View view;
    private JokeContract.Interactor interactor;
    private JokesDataSource jokesDataSource;
    private SharedPreferences sharedPreferences;

    JokePresenter(Context context, JokeContract.View view, JokesDataSource jokesDataSource) {
        this.view = view;
        this.interactor = new JokeInteractor();
        this.jokesDataSource = jokesDataSource;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    // Requests Joke data from server via JokeInteractor
    @Override
    public void requestDataFromServer(String category) {
        view.showProgress();
        interactor.getJoke(this, category);
    }

    @Override
    public void saveJokeToDB(Joke joke) {
        Joke newJoke = new Joke(joke.getId(), joke.getCategoryName(), joke.getValue(), true);
        jokesDataSource.saveJoke(newJoke);
    }

    @Override
    public void deleteJokeFromDB(Joke joke) {
        jokesDataSource.deleteJoke(joke.getId());
    }

    @Override
    public void loadJokesFromDb(String category) {
        jokesDataSource.getJokesFromCategory(category, new JokesDataSource.LoadJokesCallback() {
            @Override
            public void onJokesLoaded(List<Joke> jokes) {
                List<String> jokeValuesDbList = new ArrayList<>();
                for (int i = 0; i < jokes.size(); i++) {
                    jokeValuesDbList.add(jokes.get(i).getValue());
                }
                processJokeValues(jokeValuesDbList);
            }

            @Override
            public void onDataNotAvailable() {
                // view.showJokeErrors
            }
        });
    }

    @Override
    public boolean isJokeFavoredInPreferences(String jokeId) {
        if (sharedPreferences.getStringSet(PrefUtils.PREF_FAVORED_JOKES, null) != null) {
            return Objects.requireNonNull(sharedPreferences.getStringSet(PrefUtils.PREF_FAVORED_JOKES, null)).contains(jokeId);
        } else return false;
    }

    private void processJokeValues(List<String> jokes) {
        if (view != null) {
            if (jokes.isEmpty()) {
                view.showNoDbJokes();
            } else {
                view.showDbJokes(jokes);
            }
        }
    }

    // On successful server result displays the data to the view and hides progressbar
    @Override
    public void onFinished(Joke joke) {
        if (view != null) {
            view.setData(joke.getId(), joke.getCategory(), joke.getValue(), joke.getIconUrl());
            view.hideProgress();
        }
    }

    // On failed server result displays error and hides progressbar
    @Override
    public void onFailure(Throwable throwable) {
        if (view != null) {
            view.onResponseFailure(throwable);
            view.hideProgress();
        }
    }
}
