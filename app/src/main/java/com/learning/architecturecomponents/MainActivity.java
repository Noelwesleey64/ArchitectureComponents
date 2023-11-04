package com.learning.architecturecomponents;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //request code for our intent
    public static final int ADD_NOTE_REQUEST = 1;

    //this code handles the result that is returned by the AddEditNoteActivity. It checks if the result is successful (RESULT_OK) and then extracts the data sent from the AddEditNoteActivity
    ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        //This is the onActivityResult method of the ActivityResultCallback. It is called when the result of the activity (in this case, the AddEditNoteActivity) is received.
        @Override
        //This line creates an ActivityResultLauncher named startForResult
        public void onActivityResult(ActivityResult result) {
            //his conditional statement checks if the result is not null and if the result code is RESULT_OK.
            // This ensures that the result was successful.
            if (result != null && result.getResultCode() == RESULT_OK) {
                //If the result is successful, this line extracts the title from the data contained within the result.
                // It uses the EXTRA_TITLE constant to access the data sent from the AddEditNoteActivity.
                String title = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_TITLE);

                //this extracts the description from the data using the EXTRA_DESCRIPTION constant.
                String description = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);

                //This line extracts the priority value from the data.
                int priority = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);



                //create a note instance and pass the values we got as a result from the AddEditNoteActivity

                Note note = new Note(title, description, priority);

                //Insert the note object in the database
                noteViewModel.insert(note);

                Toast.makeText(MainActivity.this, "Note saved", Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_SHORT).show();
            }

        }
    });

    //this code handles the result that is returned by the AddEditNoteActivity. It checks if the result is successful (RESULT_OK) and then extracts the data sent from the AddEditNoteActivity
    ActivityResultLauncher<Intent> startForEditResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        //This is the onActivityResult method of the ActivityResultCallback. It is called when the result of the activity (in this case, the AddEditNoteActivity) is received.
        @Override
        //This line creates an ActivityResultLauncher named startForResult
        public void onActivityResult(ActivityResult result) {
            //his conditional statement checks if the result is not null and if the result code is RESULT_OK.
            // This ensures that the result was successful.
            if (result != null && result.getResultCode() == RESULT_OK) {
                //If the result is successful, this line extracts the title from the data contained within the result.
                // It uses the EXTRA_TITLE constant to access the data sent from the AddEditNoteActivity.
                String title = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_TITLE);

                //this extracts the description from the data using the EXTRA_DESCRIPTION constant.
                String description = result.getData().getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);

                //This line extracts the priority value from the data.
                int priority = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

                //this line extracts the id value from the data
                //we place -1 so that whe the ID is invalid the update will not happen
                int id = result.getData().getIntExtra(AddEditNoteActivity.EXTRA_ID, -1);
                   if(id == -1){
                       Toast.makeText(MainActivity.this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                       return;
                   }


                //create a note instance and pass the values we got as a result from the AddEditNoteActivity

                Note note = new Note(title, description, priority);
                 //we set the ID, room needs the id so as to carry out update
                note.setId(id);
                //Insert the note object in the database
                noteViewModel.updateNote(note);

                Toast.makeText(MainActivity.this, "Note Updated", Toast.LENGTH_SHORT).show();


            } else {

                Toast.makeText(MainActivity.this, "Note not saved", Toast.LENGTH_SHORT).show();
            }

        }
    });




    private NoteViewModel noteViewModel;

    FloatingActionButton buttonAddNote;

    //declaring a recyclerView
    RecyclerView note_recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAddNote = findViewById(R.id.button_add_note);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                //initiate the launching of the AddEditNoteActivity for a result by calling the launch method of the ActivityResultLauncher.
                //The AddEditNoteActivity will perform its actions, and upon completion, it will send back a result to the calling activity.
                //The result is handled in the onActivityResult method of the ActivityResultCallback
                startForResult.launch(intent);

            }
        });

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

        //This allows the user to swipe items in the RecyclerView either to the left or right to perform a certain action, in this case, deleting a note.
        //Create an instance of ItemTouchHelper which handles touch interactions with a recyclerView
        //creats a SimpleCallBack with dragDirs set to 0 which disables drag-and-drop functionality
        //ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT is used for swipeDirs,which allows swiping to both the left and right.
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            //This method is called when an item is dragged. In this implementation, it simply returns false, indicating that drag-and-drop functionality is not enabled.
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //This method is called when an item is swiped.
            //It deletes the note associated with the swiped item using a noteViewModel instance.
            //The adapter.getNoteAT(viewHolder.getAdapterPosition()) method is used to get the note at the swiped item's position.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                noteViewModel.delete(adapter.getNoteAT(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
         //This method attaches the ItemTouchHelper to a specific RecyclerView named note_recyclerView.
        }).attachToRecyclerView(note_recyclerView);

        adapter.setOnitemClickListener(new RecyclerViewAdapter.OnitemClickListener() {
            @Override
            public void onItemClick(Note note) {
                //we press shift + f6 to refactor
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                intent.putExtra(AddEditNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddEditNoteActivity.EXTRA_TITLE, note.getTitle());
                intent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION, note.getDescription());
                intent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY, note.getPriority());

                //initiate the launching of the AddEditNoteActivity for a result by calling the launch method of the ActivityResultLauncher.
                //The AddEditNoteActivity will perform its actions, and upon completion, it will send back a result to the calling activity.
                //The result is handled in the onActivityResult method of the ActivityResultCallback
                startForEditResult.launch(intent);

            }
        });

    }


}
