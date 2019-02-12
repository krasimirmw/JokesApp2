package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.local.JokesLocalDataSource;

public class JokePresenter implements JokeContract.Presenter, JokeContract.Interactor.OnFinishedListener {

    private JokeContract.View view;
    private JokeContract.Interactor interactor;
    private JokesLocalDataSource jokesLocalDataSource;
    private JokesHelper jokesHelper;

    JokePresenter(JokeContract.View view, JokesHelper jokesHelper, JokesLocalDataSource jokesLocalDataSource) {
        this.view = view;
        this.interactor = new JokeInteractor();
        this.jokesLocalDataSource = jokesLocalDataSource;
        this.jokesHelper = jokesHelper;
    }

    // Requests Joke data from server via JokeInteractor
    @Override
    public void requestDataFromServer(String category) {
        view.showProgress();
        interactor.getJoke(this, category);
    }

    @Override
    public void saveJokeToDB(Joke joke) {
        jokesHelper.setJokeFavored(joke, true);
        jokesLocalDataSource.saveJoke(joke);
    }

    @Override
    public void deleteJokeFromDB(Joke joke) {
        jokesHelper.setJokeFavored(joke, false);
        jokesLocalDataSource.deleteJoke(joke.getId());
    }

    // On succesful server result displays the data to the view and hides progressbar
    @Override
    public void onFinished(String category, String jokeString, String iconUrl) {
        if (view != null) {
            view.setData(category, jokeString, iconUrl);
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
