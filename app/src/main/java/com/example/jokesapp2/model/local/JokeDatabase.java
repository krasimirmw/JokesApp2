package com.example.jokesapp2.model.local;

import android.content.Context;

import com.example.jokesapp2.model.Joke;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Joke.class}, version = 1, exportSchema = false)
public abstract class JokeDatabase extends RoomDatabase {

    private static JokeDatabase INSTANCE;

    public abstract JokeDAO jokeDAO();

    private static final Object sLock = new Object();

    public static JokeDatabase getInstance(Context context) {
        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), JokeDatabase.class, "Jokes.db").build();
            }
            return INSTANCE;
        }
    }
}
