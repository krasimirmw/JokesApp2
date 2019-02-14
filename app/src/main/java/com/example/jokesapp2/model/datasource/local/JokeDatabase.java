package com.example.jokesapp2.model.datasource.local;

import android.content.Context;

import com.example.jokesapp2.model.Joke;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/* Singleton for Room database */
@Database(entities = {Joke.class}, version = 1)
public abstract class JokeDatabase extends RoomDatabase {

    private static JokeDatabase INSTANCE;

    public abstract JokeDAO jokeDAO();

    private static final Object sLock = new Object();

    public static JokeDatabase getInstance(final Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JokeDatabase.class, "Jokes.db").build();
            }
            return INSTANCE;
        }
    }
}
