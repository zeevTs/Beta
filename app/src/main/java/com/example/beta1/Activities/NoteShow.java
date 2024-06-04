package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beta1.Helpers.NoteAdapter;
import com.example.beta1.Helpers.NotificationAdapter;
import com.example.beta1.Objs.Note;
import com.example.beta1.Objs.Notification;
import com.example.beta1.R;

import java.util.ArrayList;

public class NoteShow extends AppCompatActivity {
    private ListView lvNotes;
    private ArrayList<Note> notesList;
    private NoteAdapter noteAdapter;
    private Intent toAddNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_show);
        initviews();
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title =item.getTitle().toString();
        if(title.equals("Notifications")){
            startActivity(new Intent(NoteShow.this,NotificationShow.class));
        }else if(title.equals("Main")){
            startActivity(new Intent(NoteShow.this,Main.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.notifyDataSetChanged();
    }
    private void initviews() {
        lvNotes = findViewById(R.id.lvNotes);
        notesList = user.getNotes();
        noteAdapter = new NoteAdapter(this,notesList);
        lvNotes.setAdapter(noteAdapter);
    }
    public void addNote(View view) {
        toAddNote = new Intent(NoteShow.this,NoteActivity.class);
        toAddNote.putExtra("flag", true);
        startActivity(toAddNote);

    }
}
