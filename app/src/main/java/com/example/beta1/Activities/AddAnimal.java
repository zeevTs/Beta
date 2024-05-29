package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refAnimals;
import static com.example.beta1.Helpers.FBRefs.refImg;
import static com.example.beta1.Helpers.FBRefs.refUsers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.beta1.Objs.Animal;
import com.example.beta1.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class AddAnimal extends AppCompatActivity {
    private String url= "";
    private UploadTask uploadTask;
    private ImageView iV1;
    private final int GALLERY_REQ_CODE= 1000;
    private final int CAMERA_PERMISSION_CODE = 100;
    private final int CAMERA_REQUEST_CODE = 101;
    private Uri imageUri;
    private ProgressDialog prog;
    private Animal animal;
    private EditText etName,etAge;
    public ArrayList<String> animalsDis ;
    private ValueEventListener animalListener;
    private Spinner sP;
    private ArrayAdapter<String> adp;
    private int kind = -1;
    private Intent iAddAnimal;
    private Button btnAddPet;
//    private Intent iCamera;
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

        btnAddPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

    private void initViews() {
        btnAddPet = findViewById(R.id.btnAddPet);
        etAge= findViewById(R.id.etAge);
        etName= findViewById(R.id.etName);
        animalsDis = new ArrayList<String>();
        sP = findViewById(R.id.sP);
        iV1 = findViewById(R.id.iV1);
        animalsDis.add("choose animals:");
        iAddAnimal = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
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

    public void add(String animalId) {
        String name = etName.getText().toString();
        String ageStr = etAge.getText().toString();
        if ( kind != -1 && !name.equals("") && !ageStr.equals("") ) {
            Double age = Double.valueOf(ageStr);

            animal = new Animal(animalId, name, kind, url, age);
            ArrayList<Animal> animals = user.getAnimals();
            animals.add(animal);
            user.setAnimals(animals);
            refUsers.child(user.getuId()).setValue(user);
        }else{
            Toast.makeText(this, "you must enter all data fields", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
    //todo list- need activity to show notifications and notes and option to delete them
    // todo list - need in addition to finish repetitive notification
    // todo list - need to add broadcast receiver in log in and option to remain logged in
    //todo list - need to check for correct inputs

    private void upload() {
        final String animalId = FirebaseDatabase.getInstance().getReference().push().getKey();
        prog =new ProgressDialog(this);
        prog.setTitle("uploading file....");
        prog.show();
        String userFile = user.getuId();
        StorageReference refImage = refImg.child(userFile).child(animalId + ".jpg");
        uploadTask = refImage.putFile(imageUri);
        Task<Uri> uriTask =uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw task.getException();
                }
                return refImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downloadUri =(Uri) task.getResult();
                    url = downloadUri.toString();
                    prog.dismiss();
                    add(animalId);
                }else{
                    prog.dismiss();
                    Toast.makeText(AddAnimal.this, "upload failed", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });

    }

    public void addGallery(View view){
        startActivityForResult(iAddAnimal,GALLERY_REQ_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null && requestCode == GALLERY_REQ_CODE){
            imageUri = data.getData();
            iV1.setImageURI(imageUri);
        }
    }

}