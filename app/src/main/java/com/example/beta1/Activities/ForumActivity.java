package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refForum;

import android.os.Bundle;
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

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        };
        refForum.addChildEventListener(cel);
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
}