package com.learning.architecturecomponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                //Update RecyclerView
                Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
            }
        });
    }
}