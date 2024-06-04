package com.example.beta1.Helpers;

import static com.example.beta1.Activities.LogIn.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.Note;
import com.example.beta1.R;

import java.util.ArrayList;

public class NoteAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Note> noteList;
    private LayoutInflater inflater;
    public NoteAdapter(Context context , ArrayList<Note> noteList){
        this.context = context;
        this.noteList = noteList;
        this.inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return  noteList.size();
    }

    @Override
    public Object getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = inflater.inflate(R.layout.custom_note_layout,null);
        TextView tvNoteName = view.findViewById(R.id.tvNoteName);
        TextView tvNoteTitle = view.findViewById(R.id.tvNoteTitle);
        ImageView ivNote = view.findViewById(R.id.ivNote);
        String notificationAnimalID = noteList.get(position).getAnimalId();
        tvNoteName.setText(user.getAnimal(notificationAnimalID).getName());
        tvNoteTitle.setText(noteList.get(position).getTitle());
        return view;
    }

}
