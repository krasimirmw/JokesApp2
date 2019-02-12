package com.example.jokesapp2.jokedetail;

import android.app.Activity;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.model.JokesDataSource;
import com.example.jokesapp2.utils.PrefUtils;

import rx.Observable;
import rx.subjects.PublishSubject;

public class JokesHelper {

    private static final PublishSubject<FavoredEvent> FAVORED_SUBJECT = PublishSubject.create();

    private final Activity activity;
    private final JokesDataSource jokesDataSource;

    public JokesHelper(Activity activity, JokesDataSource jokesDataSource) {
        this.activity = activity;
        this.jokesDataSource = jokesDataSource;
    }

    public Observable<FavoredEvent> getFavoredObservable() {
        return FAVORED_SUBJECT.asObservable();
    }

    public void setJokeFavored(Joke joke, boolean favored) {
        joke.setFavored(favored);
        if (favored) {
            jokesDataSource.saveJoke(joke);
            PrefUtils.addToFavorites(activity, joke.getId());
        } else {
            jokesDataSource.deleteJoke(joke.getId());
            PrefUtils.removeFromFavorites(activity, joke.getId());
        }
        FAVORED_SUBJECT.onNext(new FavoredEvent(joke.getId(), favored));
    }

    public static class FavoredEvent {
        public String jokeId;
        public boolean favored;

        public FavoredEvent(String jokeId, boolean favored) {
            this.jokeId = jokeId;
            this.favored = favored;
        }
    }

}
