package com.learning.architecturecomponents;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//creating Dao class
@Dao
public interface NoteDao {


    // It tells Room to generate the necessary
    // SQL code for inserting a record into the associated database table.
    @Insert
    void insert(Note note);

    //generate a method for updating records
    @Update
    void updateNote(Note note);

    //generate a method to delete an object from the database
    @Delete
    void delete(Note note);

    //Query to Delete all notes if the deleteAllNotes function is called
    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    //the query itself retrieves data from a table named note_table and orders
    //the results by the priority column in descending order
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    //The getAllNotes method is declared to return a LiveData object containing a list of Note objects
    //When you return data as LiveData, it can be observed by components like ViewModels and automatically
    // updated when the data changes in the database.
    LiveData<List<Note>> getAllNotes();



}
