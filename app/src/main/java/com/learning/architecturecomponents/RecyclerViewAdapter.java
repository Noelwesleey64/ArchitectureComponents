package com.learning.architecturecomponents;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Note> notes = new ArrayList<>();

    //This line declares a private member variable named listener of type OnitemClickListener.
    // This variable is used to store an instance of an interface that defines a callback method to handle item click events.
    private OnitemClickListener listener;
    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //This line is used to inflate a layout resource (specified by R.layout.notes_layout) and create a View object based on that layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        //getting the note object based on it's position in the list
        Note currentNote = notes.get(position);

        //setting the viewItems in the viewholder according their values in the note object
        holder.title.setText(currentNote.getTitle());

        //coverting the value of priority in the note object to string then binding it onto priority textview
        holder.priority.setText(String.valueOf(currentNote.getPriority()));
        //getting the
        holder.description.setText(currentNote.getDescription());


    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
        //This method is called to inform the RecyclerView that the underlying data has changed.
        // When you update the data source using setNotes,
        // you want the RecyclerView to refresh its view to reflect the changes.
        notifyDataSetChanged();
    }

    //get the note object at a certain position in the notes list
    public Note getNoteAT(int position){
        return notes.get(position);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView priority;

        private TextView description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //initializing the View Items
            title = itemView.findViewById(R.id.Title);
            priority = itemView.findViewById(R.id.Priority);
            description = itemView.findViewById(R.id.Description);

          //This code sets an OnClickListener on the itemView. When a user clicks on the item,
            //the onClick method within this anonymous class is called.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION) {
                        //defines a single method, onItemClick, which takes a Note as a parameter.
                        listener.onItemClick(notes.get(position));
                    }
                }
            });


        }
    }

    //This method allows an external class, such as an activity or fragment,
    // to set an OnitemClickListener on the ViewHolder.
    public interface OnitemClickListener{
        void onItemClick(Note note);
    }

 //his method allows an external class, such as an activity or fragment, to set an OnitemClickListener on the ViewHolder.
    public void setOnitemClickListener(OnitemClickListener listener){

        //This line assigns the provided listener to the listener member variable,
        // which allows the ViewHolder to call the onItemClick method of the listener when a list item is clicked.
               this.listener = listener;
    }
}
