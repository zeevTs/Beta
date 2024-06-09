package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.beta1.Helpers.AnimalAdapter;
import com.example.beta1.Helpers.NetworkStateReceiver;
import com.example.beta1.Objs.Animal;
import com.example.beta1.R;

import java.util.ArrayList;

public class Main extends AppCompatActivity {
    private ListView lvanimals;
    private ArrayList<Animal> animalsList;
    private AnimalAdapter animalAdapter;
    private Intent toNote, toNotification;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

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
            startActivity(new Intent(Main.this,NotificationShow.class));
        }else if(title.equals("Notes")){
            startActivity(new Intent(Main.this,NoteShow.class));
        } else if (title.equals("Forum")) {
            startActivity(new Intent(Main.this,ForumActivity.class));
        }else if (title.equals("Profile")) {
            startActivity(new Intent(Main.this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        animalAdapter.notifyDataSetChanged();
    }

    private void initViews() {
        lvanimals = findViewById(R.id.lVanimal);
        animalsList = user.getAnimals();
        animalAdapter = new AnimalAdapter(this,animalsList);
        lvanimals.setAdapter(animalAdapter);

    }

    public void addPet(View view) {
    Intent toAdd = new Intent(Main.this,AddAnimal.class);
    startActivity(toAdd);
    }





}