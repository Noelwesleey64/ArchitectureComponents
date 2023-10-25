package com.learning.architecturecomponents;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//Java class that represents the repository for the "Note" database operations.
public class NoteRepository {
    //Variable to store our NoteDao Object
    private NoteDao noteDao;

    //his variable is intended to store a list of all notes from the database,
    // and it's wrapped in a LiveData object, which allows for easy observation and updating of the data.
    private LiveData<List<Note>> allNotes;

    //creating a constructor
    //This constructor is typically used to initialize the repository and set up the database connection.
    public NoteRepository(Application application){
        //initializes the NoteDatabase object by calling its getInstance method.
        //getInstance method is often implemented as a Singleton pattern to ensure that there is only one instance of the database throughout the application's lifecycle.
        NoteDatabase database = NoteDatabase.getInstance(application);
        //initializes noteDao variable with NoteDao instance
        noteDao = database.noteDao();

        //retrieves a list of all "Note" objects from the database and stores in allNotes variable
        allNotes = noteDao.getAllNotes();

    }

    //declaring a method to insert a note
    public void insertNoteInBackground(Note note){
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
                noteDao.insert(note);

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


    //declaring a method to update a note in background
    public void UpdateNoteInBackground(Note note){
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

                //calling a method to update a note in database
                noteDao.updateNote(note);

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

    //declaring a method to delete a single note from the database
    public void deleteNoteInBackground(Note note){
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

                //calling a method to delete a note object from the database
                noteDao.delete(note);

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

    //declaring a method to delete all notes in background
    public void deleteAllNoteInBackground(Note note){
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

                //Calling a method to delete all notes in the dtatabase
                noteDao.deleteAllNotes();

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

    //declaring method to return our notes list, which are wrapped in livedata
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

}
