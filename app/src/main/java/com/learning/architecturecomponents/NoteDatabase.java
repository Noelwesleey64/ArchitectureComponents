package com.learning.architecturecomponents;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//annotation that specifies that the database will contain entities of the Note class and is of version 1.
@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    //This is a private static field that holds a reference to the single instance of the NoteDatabase.
    // This is done to ensure that there is only one database instance throughout the application's lifecycle.
    private static NoteDatabase instance;

    //This is an abstract method that returns a Data Access Object (DAO) for the Note entity
    //it will help us access Dao Objects
    public abstract NoteDao noteDao();

  //This is a static method for obtaining the singleton instance of the NoteDatabase.
    //inside the getInstance method, it checks if the instance is null.

    public static synchronized NoteDatabase getInstance(Context context){
                   //If it's null, it creates a new instance using Room.databaseBuilder.
                   //fallbackToDestructiveMigration() is used to handle database schema migrations.
                   // In this case, it simply destroys and recreates the database when the schema changes
                   // (not recommended for production use, but useful for quick development and prototyping).
                  if(instance==null){
                      instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").fallbackToDestructiveMigration().build();

                  }
                  //return the instance if not null
                  return instance;
    }
}
