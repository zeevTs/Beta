package com.example.beta1.Activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beta1.Helpers.NetworkStateReceiver;
import com.example.beta1.R;

public class DetailedNote extends AppCompatActivity {
    TextView noteTitle,noteData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_note);
        initViews();

        // sends internet state to NetworkStateReceiver class
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
    }

    private void initViews() {
        noteData = findViewById(R.id.noteData);
        noteTitle = findViewById(R.id.noteTitle)  ;
        String data = getIntent().getExtras().getString("Data","");
        String title = getIntent().getExtras().getString("Title","");
        noteData.setText(data);
        noteTitle.setText(title);
    }

    public void goToNotes(View view) {
        Intent intent= new Intent(DetailedNote.this, NoteShow.class);
        startActivity(intent);
    }
}