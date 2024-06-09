package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beta1.Helpers.NetworkStateReceiver;
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
        // sends internet state to NetworkStateReceiver class
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
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
        }else if (title.equals("Forum")) {
            startActivity(new Intent(NoteShow.this,ForumActivity.class));
        }else if (title.equals("Profile")) {
            startActivity(new Intent(NoteShow.this,ProfileActivity.class));
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
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NoteShow.this, DetailedNote.class);
                intent.putExtra("Title",notesList.get(position).getTitle().toString());
                intent.putExtra("Data",notesList.get(position).getData().toString());
                startActivity(intent);
            }
        });
    }
    public void addNote(View view) {
        toAddNote = new Intent(NoteShow.this,NoteActivity.class);
        toAddNote.putExtra("flag", true);
        startActivity(toAddNote);

    }
}
