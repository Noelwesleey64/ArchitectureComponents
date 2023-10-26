package com.learning.architecturecomponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    //declaring a recyclerView
    RecyclerView note_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializing our recyclerView item
        note_recyclerView = findViewById(R.id.recyclerView);

        //set a linear layout manager for our RecyclerView itemView
        note_recyclerView.setLayoutManager(new LinearLayoutManager(this));

        note_recyclerView.setHasFixedSize(true);

     //Creating a RecyclerViewAdapter object
        RecyclerViewAdapter adapter = new RecyclerViewAdapter();

        //set the adapter of our RecyclerView Item to the adapter object we created
        note_recyclerView.setAdapter(adapter);

        //creating an instance of a NoteViewModel using the ViewModelProvider.
        //it scopes an instance of the view model and ties it to the lifecycle of your UI component.
        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);

        //This code is setting up an observer for changes in the list of notes, u
        // Using LiveData and an Observer pattern.
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            //When the data in the LiveData changes, the observer's onChanged method is called
            //we define what will happen if when the list of notes changes
            public void onChanged(List<Note> notes) {
                //the adapter's list is updated with the new list of notes.
                //This allows for real-time updates in the UI based on changes in the underlying data.
                adapter.setNotes(notes);
            }
        });
    }
}