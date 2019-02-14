package com.example.jokesapp2.model.datasource.local;

import com.example.jokesapp2.model.Joke;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/*
* Data access object for Joke
 */
@Dao
public interface JokeDAO {

    /**
     * Gets joke with selected Id
     * @param jokeId Id of the joke
     * @return Joke object from database
     */
    @Query("Select * FROM JOKES WHERE entryid = :jokeId")
    Joke getJokeById(String jokeId);

    /**
     * Select all jokes from the jokes table and select them by categoryname
     *
     * @return jokes from selected category.
     */
    @Query("Select * FROM Jokes WHERE categoryname = :category")
    List<Joke> getJokesFromCategory(String category);

    /* Saves joke in database. If joke already exist it will get replaced */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveJoke(Joke joke);

    /* Deletes a joke from the database with the requested Id */
    @Query("DELETE FROM Jokes WHERE entryid = :jokeId")
    void deleteJoke(String jokeId);
}
