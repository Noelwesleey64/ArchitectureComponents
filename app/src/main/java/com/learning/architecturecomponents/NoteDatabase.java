package com.learning.architecturecomponents;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
                      instance = Room.databaseBuilder(context.getApplicationContext(), NoteDatabase.class, "note_database").fallbackToDestructiveMigration().addCallback(roomCallback).build();

                  }
                  //return the instance if not null
                  return instance;
    }


    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        //Press CTRL + O and choose create
        //called when the Room database is created for the first time.
        //Inside this method, you can define actions or initializations that should take place when the database is created.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //populate the database when it is created
            insertNoteInBackground(new Note("Title 1", "Description 1", 1), instance);
            insertNoteInBackground(new Note("Title 2", "Description 2", 2), instance);
            insertNoteInBackground(new Note("Title 3", "Description 3", 3), instance);

        }
    };

    //declaring a method to insert a note
    //we want to populate the database when it is created
    public static void insertNoteInBackground(Note note, NoteDatabase db){

        //create a single-threaded ExecutorService to manage background tasks.
        //It creates an ExecutorService with a single background thread, ensuring that database operations do not block the main UI thread.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // is creating a Handler object associated with the main (UI) thread's Looper.
        //Looper is a class in Android that manages message processing within a thread.
        //Looper.getMainLooper() retrieves the Looper for the main thread.
        //A Handler is a class that is used to communicate between background threads and the main (UI) thread in Android.
        // It allows you to post Runnable objects for execution on the main thread.

        Handler handler = new Handler(Looper.getMainLooper());
        //he Handler is used to post a Runnable on the main thread once the background database operation is complete.
        //This code starts the execution of a background task using an ExecutorService.
        executorService.execute(new Runnable() {
            //his method contains the code that will be executed in the background thread.
            @Override
            public void run() {

                // It is a method that performs an "insert" operation in the database table associated with the Note entity, adding a new record with the provided Note data
                db.noteDao().insert(note);

                //on finishing task
                //After the database operation is completed, this code posts a new Runnable to a handler.
                //he handler is typically used to perform actions on the main (UI) thread from a background thread.
                handler.post(new Runnable() {
                    @Override
                    public void run() {


                    }
                });
            }
        });
    }

}
