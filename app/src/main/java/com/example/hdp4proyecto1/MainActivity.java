package com.example.hdp4proyecto1;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.base.Strings;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");
    EditText etCedula;
    Button btnVotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etCedula = findViewById(R.id.et_cedula);
        btnVotar = findViewById(R.id.btn_votar);
        btnVotar.setOnClickListener(view -> {
            if (etCedula.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Ingrese su cédula",
                        Toast.LENGTH_LONG).show();
            } else if (!etCedula.getText().toString().contains("-")) {
                Toast.makeText(getApplicationContext(), "Debe ingresar su cédula con guion",
                        Toast.LENGTH_LONG).show();
            } else {
                String regex = "^(PE|E|N|[23456789](?:AV|PI)?|1[0123]?(?:AV|PI)?)-(\\d{1,4})-(\\d{1,6})$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(etCedula.getText().toString());
                boolean matched = matcher.find();
                if (matched) {
                    String[] parts = etCedula.getText().toString().split("-");
                    String part1 = Strings.padStart(parts[0], 2, '0');
                    String part2 = Strings.padStart(parts[1], 4, '0');
                    String part3 = Strings.padStart(parts[2], 6, '0');
                    String cedulaZeros = part1 + "-" + part2 + "-" + part3;
                    Query queryCedula = myRef.orderByChild("Cedula").equalTo(cedulaZeros);
                    Log.d("Cédula", cedulaZeros);
                    queryCedula.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String uid = childSnapshot.getKey();
                                    Boolean votado = childSnapshot.child("Votado").getValue(Boolean.class);
                                    Log.d("Firebase database", "Key is: " + uid);
                                    Toast.makeText(getApplicationContext(), Boolean.TRUE.equals(votado) ? "Ha votado" : "No ha votado", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Cédula no encontrada", Toast.LENGTH_LONG).show();
                                Log.d("Firebase database", "No existe");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Failed to read value
                            Log.w("Firebase database", "Failed to read value.", error.toException());
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "Debe ingresar una cédula válida",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}