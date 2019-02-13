package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.datasource.JokesDataSource;

import java.util.ArrayList;
import java.util.List;

public class JokePresenter implements JokeContract.Presenter, JokeContract.Interactor.OnFinishedListener {

    private JokeContract.View view;
    private JokeContract.Interactor interactor;
    private JokesDataSource jokesDataSource;

    JokePresenter(JokeContract.View view, JokesDataSource jokesDataSource) {
        this.view = view;
        this.interactor = new JokeInteractor();
        this.jokesDataSource = jokesDataSource;
    }

    // Requests Joke data from server via JokeInteractor
    @Override
    public void requestDataFromServer(String category) {
        view.showProgress();
        interactor.getJoke(this, category);
    }

    @Override
    public void saveJokeToDB(Joke joke) {
        Joke newJoke = new Joke(joke.getId(), joke.getCategoryName(), joke.getValue(), joke.isFavored());
        jokesDataSource.saveJoke(newJoke);
    }

    @Override
    public void deleteJokeFromDB(Joke joke) {
        jokesDataSource.deleteJoke(joke.getId());
    }

    @Override
    public void loadJokesFromDb() {
        jokesDataSource.getJokes(new JokesDataSource.LoadJokesCallback() {
            @Override
            public void onJokesLoaded(List<Joke> jokes) {
                List<Joke> jokeDbList = new ArrayList<Joke>();
                jokeDbList.addAll(jokes);
            }

            @Override
            public void onDataNotAvailable() {
                // view.showJokeErrors
            }
        });
    }

    private void processJokes(List<Joke> jokes) {
        if (jokes.isEmpty()) {
            //view.showNoJokes
        }
        else {
            //view.showJokes
        }
    }

    // On successful server result displays the data to the view and hides progressbar
    @Override
    public void onFinished(Joke joke) {
        if (view != null) {
            view.setData(joke.getId(), joke.getCategory(), joke.getValue(), joke.getIconUrl(), joke.isFavored());
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
