package com.example.ergasia2_android_20202021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    EditText editText1, editText2, editText3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        editText1 = findViewById(R.id.editTextTextPersonName);
        editText2 = findViewById(R.id.editTextTextPassword);
        editText3 = findViewById(R.id.editTextTextPersonName3);
    }

    public void signup(View view){//Εγγραφή νέου χρήστη
        mAuth.createUserWithEmailAndPassword(
                editText1.getText().toString(), editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            currentUser = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(),"Εγγραφήκατε με επιτυχία!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    public void signin(View view){//Εισαγωγή ήδη υπάρχοντος χρήστη
        mAuth.signInWithEmailAndPassword(
                editText1.getText().toString(),editText2.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(editText3.getText().toString().matches("")){//Υποχρεωτική εισαγωγή στο πεδίο αυτό
                                Toast.makeText(getApplicationContext(),"Εισάγετε όνομα και διεύθυνση!", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getApplicationContext(),"Είσοδος επιτυχής!", Toast.LENGTH_LONG).show();
                                currentUser = mAuth.getCurrentUser();
                                addNameAndAdressToUser(editText3.getText().toString(), currentUser);
                            Intent intent = new Intent(getApplicationContext(),MainActivity2.class);
                            intent.putExtra("key1", editText3.getText().toString());
                            startActivity(intent);}
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void addNameAndAdressToUser(String NameAndAddress, FirebaseUser user){//Εισαγώγη πεδίου ονόματος και διεύθυνσης στους χρήστες
        UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(NameAndAddress)
                .build();
        user.updateProfile(profileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"Όνομα και διεύθυνση ανανεώθηκαν!", Toast.LENGTH_LONG).show();
                    }
                });
    }
}