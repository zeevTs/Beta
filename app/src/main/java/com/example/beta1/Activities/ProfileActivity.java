package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beta1.Helpers.NetworkStateReceiver;
import com.example.beta1.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    private TextView userName,userCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initViews();

        // sends internet state to NetworkStateReceiver class
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
    }

    private void initViews() {
        userName = findViewById(R.id.userName);
        userCity = findViewById(R.id.userCity);
        String name = user.getName();
        String city = user.getCity();
        userCity.setText("city: "+city);
        userName.setText("user: "+name);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String title =item.getTitle().toString();
        if(title.equals("Main")){
            startActivity(new Intent(ProfileActivity.this,Main.class));
        }else if(title.equals("Notes")){
            startActivity(new Intent(ProfileActivity.this,NoteShow.class));
        }else if (title.equals("Forum")) {
            startActivity(new Intent(ProfileActivity.this,ForumActivity.class));
        }else if (title.equals("Notifications")) {
            startActivity(new Intent(ProfileActivity.this,NotificationShow.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void SignOut(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent toLogin = new Intent(ProfileActivity.this, LogIn.class);
        startActivity(toLogin);
        finish();
    }
}