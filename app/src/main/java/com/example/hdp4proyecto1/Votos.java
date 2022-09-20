package com.example.hdp4proyecto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Votos extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");
    Long votosVivian, votosMartin, votosOmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votos);
        Query queryVivian = myRef.orderByChild("Cedula").equalTo("Vivían Valenzuela");
        Query queryMartin = myRef.orderByChild("Cedula").equalTo("Martín Candanedo");
        Query queryOmar = myRef.orderByChild("Cedula").equalTo("Omar Aizpurua");
        queryVivian.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Votos", "Conteo para Vivian: "+snapshot.getChildrenCount());
                votosVivian = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        queryMartin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Votos", "Conteo para Martian: "+snapshot.getChildrenCount());
                votosMartin = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        queryOmar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("Votos", "Conteo para Omar: "+snapshot.getChildrenCount());
                votosOmar = snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
    }
}