package com.example.hdp4proyecto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");
    EditText cedula;
    Button votar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cedula = findViewById(R.id.et_cedula);
        votar = findViewById(R.id.btn_votar);
        votar.setOnClickListener(view -> {
            String[] parts = cedula.getText().toString().split("-");
            String part1 = String.format(Locale.getDefault(), "%02d", Integer.parseInt(parts[0]));
            String part2 = String.format(Locale.getDefault(),"%04d", Integer.parseInt(parts[1]));
            String part3 = String.format(Locale.getDefault(),"%06d", Integer.parseInt(parts[2]));
            String cedulaZeros = part1 + "-" + part2 + "-" + part3;
            Query queryCedula = myRef.orderByChild("cedula").equalTo(cedulaZeros);
            Log.d("Cedula", cedulaZeros);
            queryCedula.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            String uid = childSnapshot.getKey();
                            Log.d("Firebase database", "Key is: " + uid);
                        }
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Cedula no encontrada", Toast.LENGTH_SHORT).show();
                        Log.d("Firebase database", "No existe");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w("Firebase database", "Failed to read value.", error.toException());
                }
            });
        });

    }
}