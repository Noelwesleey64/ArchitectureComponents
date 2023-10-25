package com.learning.architecturecomponents;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;


public class NoteViewModel extends AndroidViewModel {
    private NoteRepository repository;
    private LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    //method to insert note in database
    public void insert(Note note){
        repository.insertNoteInBackground(note);
    }

    //method to update note in database
    public void updateNote(Note note){
        repository.UpdateNoteInBackground(note);
    }

    //method to delete note in database
    public void delete(Note note){
        repository.deleteNoteInBackground(note);
    }

    //method to deleteAll notes in database
    public void deleteAll(){
        repository.deleteAllNoteInBackground();
    }

    //method to return a list of notes wrapped in livedata
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}
