package com.example.beta1.Activities;

import static com.example.beta1.Activities.LogIn.user;
import static com.example.beta1.Helpers.FBRefs.refAnimals;
import static com.example.beta1.Helpers.FBRefs.refImg;
import static com.example.beta1.Helpers.FBRefs.refUsers;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.net.ConnectivityManager;
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
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import com.example.beta1.Helpers.NetworkStateReceiver;
import com.example.beta1.Objs.Animal;
import com.example.beta1.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AddAnimal extends AppCompatActivity {
    private Intent iCamera;
    private String url= "";
    private UploadTask uploadTask,uploadTask2;
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
    private Bitmap bitmap;
    private boolean flag;

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
                if (flag) {
                    upload();
                }else {
                    upload2();
                }
            }
        });

        // sends internet state to NetworkStateReceiver class
        NetworkStateReceiver networkStateReceiver = new NetworkStateReceiver();
        IntentFilter connectFilter = new IntentFilter();
        connectFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkStateReceiver, connectFilter);
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
    public void upload2(){
        final String animalId2 = FirebaseDatabase.getInstance().getReference().push().getKey();
        prog =new ProgressDialog(this);
        prog.setTitle("uploading file....");
        prog.show();
        String userFile2 = user.getuId();
        StorageReference refImage = refImg.child(userFile2).child(animalId2 + ".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        uploadTask2 = refImage.putBytes(data);
        Task<Uri> uriTask =uploadTask2.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
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
                    add(animalId2);
                }else{
                    prog.dismiss();
                    Toast.makeText(AddAnimal.this, "upload failed", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }
    public void addCamera(View view){

        askCamPer();

    }

    public void addGallery(View view){
        startActivityForResult(iAddAnimal,GALLERY_REQ_CODE);
    }
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data!=null && requestCode == CAMERA_REQUEST_CODE){
            bitmap = (Bitmap) data.getExtras().get("data");
            iV1.setImageBitmap(bitmap);
            flag = false;
        }
        else if(resultCode == RESULT_OK && data!=null && requestCode == GALLERY_REQ_CODE){
            imageUri = data.getData();
            iV1.setImageURI(imageUri);
            flag = true;
        }
    }
    private void askCamPer() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},CAMERA_PERMISSION_CODE);

        }else{
            openCam();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length<0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCam();
            }else{
                Toast.makeText(this, "Camera Permission is Required to Use camera ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openCam() {
        iCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(iCamera,CAMERA_REQUEST_CODE);
    }

}