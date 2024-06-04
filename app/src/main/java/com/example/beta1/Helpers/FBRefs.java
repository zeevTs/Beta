package com.example.beta1.Helpers;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class FBRefs {

    public static FirebaseAuth refAuth = FirebaseAuth.getInstance();
    public static FirebaseDatabase FBDB = FirebaseDatabase.getInstance();

    public static DatabaseReference refUsers = FBDB.getReference("Users");
    public static DatabaseReference refAnimals = FBDB.getReference("Tables/Animals");
    public static FirebaseStorage FBST = FirebaseStorage.getInstance();
    public static StorageReference refSt = FBST.getReference();
    public static StorageReference refImg = refSt.child("images");
    public static DatabaseReference refForum = FBDB.getReference("Forum");

}