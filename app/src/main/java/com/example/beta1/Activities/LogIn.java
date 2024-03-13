package com.example.beta1.Activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.example.beta1.FBRefs.refAuth;
import static com.example.beta1.FBRefs.refUsers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.beta1.Objs.User;
import com.example.beta1.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.Query;

public class LogIn extends AppCompatActivity {
    private EditText etEmail,etPas,etName,etCity;
    private Button btnLog,btnSign;
    private TextView signClick, logClick,logTv,signTv;
    public static User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initViews();
        SpannableString ss = new SpannableString(logClick.getText().toString());
        ClickableSpan cS = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                logTv.setVisibility(View.GONE);
                signTv.setVisibility(View.VISIBLE);
                etName.setVisibility(View.VISIBLE);
                etCity.setVisibility(View.VISIBLE);
                btnLog.setVisibility(View.GONE);
                btnSign.setVisibility(View.VISIBLE);
                logClick.setVisibility(View.GONE);
                signClick.setVisibility(View.VISIBLE);
            }
        };
        ss.setSpan(cS,29,36,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        logClick.setText(ss);
        logClick.setMovementMethod(LinkMovementMethod.getInstance());

        SpannableString ss2 = new SpannableString(signClick.getText().toString());
        ClickableSpan cS2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                signTv.setVisibility(View.GONE);
                logTv.setVisibility(View.VISIBLE);
                etName.setVisibility(View.GONE);
                etCity.setVisibility(View.GONE);
                btnSign.setVisibility(View.GONE);
                btnLog.setVisibility(View.VISIBLE);
                signClick.setVisibility(View.GONE);
                logClick.setVisibility(View.VISIBLE);
            }
        };
        ss2.setSpan(cS2,23,29,  Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signClick.setText(ss2);
        signClick.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initViews() {
        logTv = findViewById(R.id.LogTv);
        signTv = findViewById(R.id.SignTv);
        logClick = findViewById(R.id.LogClick);
        signClick = findViewById(R.id.SignClick);
        etEmail = findViewById(R.id.etEmail);
        etPas = findViewById(R.id.etPas);
        etName = findViewById(R.id.etName);
        etCity = findViewById(R.id.etCity);
        btnLog = findViewById(R.id.btnLog);
        btnSign = findViewById(R.id.btnSign);
    }


    public void logIn(View view) {
        String email = etEmail.getText().toString();
        String password = etPas.getText().toString();

        refAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LogIn.this, "Logged in successfully ", Toast.LENGTH_SHORT).show();
                            String userId = refAuth.getCurrentUser().getUid();
                            Query query = refUsers.equalTo(userId).limitToFirst(1);
                            query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DataSnapshot dS = task.getResult();
                                        for (DataSnapshot data : dS.getChildren()) {
                                             user = data.getValue(User.class);
                                        }
                                        Intent si = new Intent(LogIn.this,Main.class);
                                        startActivity(si);
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(LogIn.this, "Log in failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void signUp(View view) {
        String email = etEmail.getText().toString();
        String password = etPas.getText().toString();
        String name = etName.getText().toString();
        String city = etCity.getText().toString();
        refAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser userFB = refAuth.getCurrentUser();
                            String uid = userFB.getUid();
                            user = new User(uid, name, city);
                            refUsers.child(uid).setValue(user);
                            Toast.makeText(LogIn.this, "user created successfully ", Toast.LENGTH_SHORT).show();
                            Intent si = new Intent(LogIn.this,Main.class);
                            startActivity(si);
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(LogIn.this, "email is already used", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(LogIn.this, "user creation failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}