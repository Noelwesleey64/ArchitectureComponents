 package com.learning.architecturecomponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

 public class AddEditNoteActivity extends AppCompatActivity {

     // static variables used as keys for passing the title, description and priority of an item or some data to our activity using an Intent.
     public static final String EXTRA_ID = "com.learning.architecturecomponents.EXTRA_ID";
     public static final String EXTRA_TITLE = "com.learning.architecturecomponents.EXTRA_TITLE";
     public static final String EXTRA_DESCRIPTION = "com.learning.architecturecomponents.EXTRA_DESCRIPTION";

      public static final String EXTRA_PRIORITY = "com.learning.architecturecomponents.EXTRA_PRIORITY";



    private EditText editTextTitle;
    private EditText editTextDescription;

    private NumberPicker numberPickerPriority;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        //initializing the itemViews
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);
        numberPickerPriority = findViewById(R.id.number_picker_priority);
        Button saveNote = findViewById(R.id.button_save_note);



        //set minimum value for number picker
        numberPickerPriority.setMinValue(1);
        //set maximum value for number picker
        numberPickerPriority.setMaxValue(10);
         Intent intent = getIntent();
         if(intent.hasExtra(EXTRA_ID)){
             setTitle("Edit Note");
             editTextTitle.setText(intent.getStringExtra(EXTRA_TITLE));
             editTextDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
             numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY, 1));

         } else{
             //This line customizes the action bar or app bar for the current activity
             //It sets the indicator on the left side of the action bar to an icon with the resource ID ic_close.
             // This icon is usually used to indicate a "close" or "cancel" action.
             //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
             //This sets the title of the current activity to "Add Note."
             setTitle("Add Note");
         }




        saveNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
                Toast.makeText(AddEditNoteActivity.this, "Notes Added to Database", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveNote() {
        //It retrieves the text entered by the user into the editTextTitle and editTextDescription EditText fields.
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        //It also retrieves the value selected by the user in the numberPickerPriority
        int priority = numberPickerPriority.getValue();

        //It checks whether the title or description is empty after trimming any leading or trailing whitespace.
        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            //If either the title or description is empty, it displays a Toast message to the user with the text "Please Insert a title and description" and exits the function.
            Toast.makeText(this, "Please Insert a title and description", Toast.LENGTH_SHORT).show();

        } else {

            //send data to the activity and it utilizes the constants defined earlier  as keys to attach specific data to the Intent
            // creating an empty Intent that you will use to attach data.
            Intent data = new Intent();
            // sending the title as an extra data item in the Intent.
            // components that receive this Intent can retrieve the title using the EXTRA_TITLE key.
            data.putExtra(EXTRA_TITLE, title);
            // sending the description as an extra data item in the Intent.
           // components that receive this Intent can retrieve the description using the EXTRA_DESCRIPTION key.
            data.putExtra(EXTRA_DESCRIPTION, description);
            // sending the priority as an extra data item in the Intent.
            // components that receive this Intent can retrieve the priority using the EXTRA_PRIORITY key.
            data.putExtra(EXTRA_PRIORITY, priority);

            //check if the id of the item to be updated passed from the main activity intent is invalid

            int id = getIntent().getIntExtra(EXTRA_ID, -1);
                if (id != -1){
                    data.putExtra(EXTRA_ID, id);
                }
            //indicates that that the above operations were successful
            setResult(RESULT_OK, data);
            //After setting the result and attaching the data to the Intent, the current activity is closed
            finish();
        }



        }


    //This method is called by the Android framework when it's time to create the options menu for the activity.
     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
        //The MenuInflater is used to inflate (create) a menu from an XML resource file.
         // It's a necessary step to work with menu resources.
         MenuInflater menuInflater = getMenuInflater();
         //essentially takes the XML menu definition and converts it into a visible menu that the user can interact with.
         menuInflater.inflate(R.menu.add_note_menu, menu);
         //This line indicates that the method execution was successful and that the menu has been created
         return true;
     }

 }