package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refUsers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.Note;
import com.example.beta1.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class NoteActivity extends AppCompatActivity {
    private Spinner spAnimals;
    private TextView notiTv;
    private Intent toNotification,toMain;
    private Button  bNoti;
    private EditText etTitle,eTdata;
    private ArrayList<String> animalsName;
    private ArrayAdapter<String> adp;
    private String data,title;
    private boolean flag;
    private String name;
    private Animal chosenAnimal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        flag = getIntent().getExtras().getBoolean("flag",true);
        initViews();
        if (!flag) {
            bNoti.setText("Choose notification time:");
        }
    }

    private void initViews() {
        eTdata = findViewById(R.id.etData);
        etTitle = findViewById(R.id.etTitle);
        notiTv = findViewById(R.id.etName);
        spAnimals = findViewById(R.id.spAnimals);
        toMain = new Intent(this,Main.class);
        bNoti = findViewById(R.id.bNoti);
        animalsName =new ArrayList<>();
        animalsName.add("choose your animal: ");
        for (int i=0;i< user.getAnimals().size();i++){
            animalsName.add(user.getAnimals().get(i).getName());
        }

        adp = new ArrayAdapter<String>(NoteActivity.this, android.R.layout.simple_spinner_item,animalsName);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spAnimals.setAdapter(adp);
        spAnimals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    chosenAnimal= user.getAnimals().get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                finish();
            }
        }
    }

    public void setNoti(View view) {

        String data = eTdata.getText().toString();
        String title = etTitle.getText().toString();
        final String notiId = FirebaseDatabase.getInstance().getReference().push().getKey();
        if (chosenAnimal == null  ) {
            Toast.makeText(this, "you must choose an animal", Toast.LENGTH_SHORT).show();
        } else {
            String animalID = chosenAnimal.getAnimalId();
            if (!data.equals("") && !title.equals("")) {
                if (flag) {
                    Note note = new Note(title, data, animalID, notiId);
                    ArrayList<Note> notes = user.getNotes();
                    notes.add(note);
                    user.setNotes(notes);
                    refUsers.child(user.getuId()).setValue(user);
                    startActivity(toMain);

                } else {
                    toNotification = new Intent(this, NotificationActivity.class);
                    toNotification.putExtra("Data", data);
                    toNotification.putExtra("Title", title);
                    toNotification.putExtra("NotiId", notiId);
                    toNotification.putExtra("AnimalId", animalID);
                    startActivityForResult(toNotification,100 );

                }

            } else {
                Toast.makeText(this, "you must enter all data fields", Toast.LENGTH_SHORT).show();
            }
        }
    }
}