package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.beta1.Helpers.AnimalAdapter;
import com.example.beta1.Helpers.NotificationAdapter;
import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.Notification;
import com.example.beta1.R;

import java.util.ArrayList;

public class NotificationShow extends AppCompatActivity {
    private ListView lvNotifications;
    private ArrayList<Notification> notificationsList;
    private NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_show);
        initViews();

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

    }
}