package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.FBRefs.refAnimals;
import static com.example.beta1.FBRefs.refAuth;
import static com.example.beta1.FBRefs.refUsers;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.beta1.Objs.Animal;
import com.example.beta1.Objs.User;
import com.example.beta1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddAnimal extends AppCompatActivity {
    private EditText etName,etAge;
    public ArrayList<String> animalsDis ;
    private ValueEventListener animalListener;
    private Spinner sP;
    private ArrayAdapter<String> adp;
    private AutoCompleteTextView aT1;
    private int kind = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_animal);
        initViews();
        refAnimals.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
               if(task.isSuccessful()){
                   DataSnapshot dS = task.getResult();
                   animalsDis.clear();
                   animalsDis.add("enter animal:");
                   for (DataSnapshot data : dS.getChildren()){
                       String value = data.getValue(String.class);
                       animalsDis.add(value);
                   }
                   adp.notifyDataSetChanged();
               }
               else{
                   Log.e("firebase","Error getting data",task.getException());
               }
            }
        });
    }

    private void initViews() {
        etAge= findViewById(R.id.etAge);
        etName= findViewById(R.id.etName);
        animalsDis = new ArrayList<String>();
        sP = findViewById(R.id.sP);
        animalsDis.add("choose animals:");
        adp = new ArrayAdapter<String>(AddAnimal.this, android.R.layout.simple_spinner_item,animalsDis);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sP.setAdapter(adp);
        sP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0){
                    kind = position-1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void add(View view) {
        String name = etName.getText().toString();
        String ageStr = etAge.getText().toString();
        if ( kind != -1 && !name.equals("") && !ageStr.equals("") ) {
            Double age = Double.valueOf(ageStr);
            String url = null;
            final String animalId = FirebaseDatabase.getInstance().getReference().push().getKey();
            Animal animal = new Animal(animalId, name, kind, url, age);
            ArrayList<Animal> animals = user.getAnimals();
            animals.add(animal);
            user.setAnimals(animals);
            refUsers.child(user.getuId()).setValue(user);

        }else{
            Toast.makeText(this, "you must enter all data fields", Toast.LENGTH_SHORT).show();
        }
    }

}