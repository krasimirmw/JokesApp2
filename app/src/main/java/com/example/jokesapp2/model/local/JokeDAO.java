package com.example.jokesapp2.model.local;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface JokeDAO {

    /**
     * Select all jokes from the jokes table
     *
     * @return all jokes.
     */
    @Query("Select * FROM Jokes")
    List<Joke> getJokes();

}
