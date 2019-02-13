package com.example.jokesapp2.model.datasource.local;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface JokeDAO {

    @Query("Select * FROM JOKES WHERE entryid = :jokeId")
    Joke getJokeById(String jokeId);

    /**
     * Select all jokes from the jokes table and select them by categoryname
     *
     * @return jokes from selected category.
     */
    @Query("Select * FROM Jokes WHERE categoryname = :category")
    List<Joke> getJokesFromCategory(String category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveJoke(Joke joke);

    @Query("DELETE FROM Jokes WHERE entryid = :jokeId")
    void deleteJoke(String jokeId);
}
