package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refUsers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beta1.Helpers.AlarmReceiver;
import com.example.beta1.Helpers.AnimalAdapter;
import com.example.beta1.Helpers.NotificationAdapter;
import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.Notification;
import com.example.beta1.R;

import java.util.ArrayList;

public class NotificationShow extends AppCompatActivity  {
    private ListView lvNotifications;
    private ArrayList<Notification> notificationsList;
    private NotificationAdapter notificationAdapter;
    private Intent toAddNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_show);
        initViews();

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
            startActivity(new Intent(NotificationShow.this,Main.class));
        }else if(title.equals("Notes")){
            startActivity(new Intent(NotificationShow.this,NoteShow.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        notificationAdapter.notifyDataSetChanged();
    }
    private void initViews() {
        lvNotifications = findViewById(R.id.lvNotifications);
        notificationsList = user.getNotifications();
        notificationAdapter = new NotificationAdapter(this,notificationsList);
        lvNotifications.setAdapter(notificationAdapter);
        lvNotifications.setOnCreateContextMenuListener(this);
    }

    public void addNotification(View view) {
        toAddNotification = new Intent(NotificationShow.this,NoteActivity.class);
        toAddNotification.putExtra("flag", false);
        startActivity(toAddNotification);
    }



}