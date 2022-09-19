package com.example.hdp4proyecto1;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Votacion extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);
        // Todo utilizar variable key del intent
        myRef.child("key").child("Votado").setValue(true);
        // Todo utilizar variable del nombre del candidato
        myRef.child("key").child("Voto").setValue("candidato");
    }
}