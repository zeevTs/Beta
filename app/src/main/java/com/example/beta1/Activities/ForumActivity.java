package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refForum;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.DialogFragment;

import com.example.beta1.Helpers.MessageAdapter;
import com.example.beta1.Helpers.NetworkStateReceiver;
import com.example.beta1.Helpers.NoteAdapter;
import com.example.beta1.Objs.Message;
import com.example.beta1.Objs.Note;
import com.example.beta1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

public class ForumActivity extends AppCompatActivity {
    private ArrayList<Message> messagesList;
    private ListView lvPosts;
    private ChildEventListener cel;
    private MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        initViews();
        cel= new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Message msg = snapshot.getValue(Message.class);
                messagesList.add(0,msg);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                Message msg = snapshot.getValue(Message.class);
                int pos = -1;
                for (int i=0; i< messagesList.size();i++){
                    if(messagesList.get(i).getMsgId().equals(msg.getMsgId())){
                        pos=i;
                    }
                }
                if( pos!=-1) {
                    messagesList.remove(pos);
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        refForum.addChildEventListener(cel);
        // sends internet state to NetworkStateReceiver class
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refForum.removeEventListener(cel);
    }

    private void initViews() {
        lvPosts = findViewById(R.id.lvPosts);
        messagesList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this,messagesList);
        lvPosts.setAdapter(messageAdapter);
    }

    public void addPost(View view) {
        DialogFragment addPostDialogFragment = new AddPostDialogFragment();
        addPostDialogFragment.show(getSupportFragmentManager(), "add post");
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
            startActivity(new Intent(ForumActivity.this,NotificationShow.class));
        }else if(title.equals("Notes")){
            startActivity(new Intent(ForumActivity.this,NoteShow.class));
        } else if (title.equals("Main")) {
            startActivity(new Intent(ForumActivity.this,Main.class));
        } else if (title.equals("Profile")) {
            startActivity(new Intent(ForumActivity.this,ProfileActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}