package com.example.jokesapp2.jokedetail;

import android.content.Context;

import com.example.jokesapp2.model.Joke;
import com.example.jokesapp2.utils.PrefUtils;

import rx.Observable;
import rx.subjects.PublishSubject;

public class JokesHelper {

    private static final PublishSubject<FavoredEvent> FAVORED_SUBJECT = PublishSubject.create();

    private final Context context;

    JokesHelper(Context context) {
        this.context = context;
    }

    public Observable<FavoredEvent> getFavoredObservable() {
        return FAVORED_SUBJECT.asObservable();
    }

    void setJokeFavored(Joke joke, boolean favored) {
        joke.setFavored(favored);
        if (favored) {
            PrefUtils.addToFavorites(context, joke.getId());
        } else {
            PrefUtils.removeFromFavorites(context, joke.getId());
        }
        FAVORED_SUBJECT.onNext(new FavoredEvent(joke.getId(), favored));
    }

    public static class FavoredEvent {
        String jokeId;
        boolean favored;

        FavoredEvent(String jokeId, boolean favored) {
            this.jokeId = jokeId;
            this.favored = favored;
        }
    }

}
