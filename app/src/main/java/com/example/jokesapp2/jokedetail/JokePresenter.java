package com.example.jokesapp2.jokedetail;

public class JokePresenter implements JokeContract.Presenter, JokeContract.Interactor.OnFinishedListener {

    private JokeContract.View view;
    private JokeContract.Interactor interactor;

    JokePresenter(JokeContract.View view, JokeContract.Interactor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    // Requests Joke data from server via JokeInteractor
    @Override
    public void requestDataFromServer(String category) {
        view.showProgress();
        interactor.getJoke(this, category);
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
