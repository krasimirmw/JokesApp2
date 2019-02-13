package com.example.jokesapp2.jokedetail;

import com.example.jokesapp2.model.Joke;

public interface JokeContract {

    /**
     * showProgress() and hideProgress() would be used for displaying and hiding the progressBar
     * setData and onResponseFailure is fetched from the JokeInteractor class
     */
    interface View {
        void showProgress();

        void hideProgress();

        void setData(String jokeId, String category, String jokeString, String drawableUrl, boolean isFavored);

        void onResponseFailure(Throwable throwable);
    }

    /**
     * Call when user interact with the view
     */
    interface Presenter {
        void requestDataFromServer(String category);

        void saveJokeToDB(Joke joke);

        void deleteJokeFromDB(Joke joke);

        void loadJokesFromDb();
    }

    /**
     * Interactors are classes built for fetching data from your database, web services, or any other data source.
     **/
    interface Interactor {

        interface OnFinishedListener {
            void onFinished(Joke joke);

            void onFailure(Throwable throwable);
        }

        void getJoke(JokeContract.Interactor.OnFinishedListener onFinishedListener, String jokeCategory);
    }

}
