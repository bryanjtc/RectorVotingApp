package com.example.hdp4proyecto1;

import android.content.Intent;
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
                // Revisa si no ingresa ningun valor en el campo
                Toast.makeText(getApplicationContext(), "Ingrese su cédula",
                        Toast.LENGTH_LONG).show();
            } else if (!etCedula.getText().toString().contains("-")) {
                // Revisa si ingresa la cedula sin guiones
                Toast.makeText(getApplicationContext(), "Debe ingresar su cédula con guion",
                        Toast.LENGTH_LONG).show();
            } else {
                // Revisa si la cedula ingresada coincide con el formato
                String regex = "^(PE|E|N|[23456789](?:AV|PI)?|1[0123]?(?:AV|PI)?)-(\\d{1,4})-(\\d{1,6})$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(etCedula.getText().toString());
                boolean matched = matcher.find();
                if (matched) {
                    // Se divide la cedula en tres partes, utilizando el simbolo - como separador y se le agregan ceros al inicio a cada parte
                    String[] parts = etCedula.getText().toString().split("-");
                    String part1 = Strings.padStart(parts[0], 2, '0');
                    String part2 = Strings.padStart(parts[1], 4, '0');
                    String part3 = Strings.padStart(parts[2], 6, '0');
                    String cedulaZeros = part1 + "-" + part2 + "-" + part3;
                    // Se busca la cedula con los ceros en la base de datos de firebase
                    Query queryCedula = myRef.orderByChild("Cedula").equalTo(cedulaZeros);
                    Log.d("Cédula", cedulaZeros);
                    queryCedula.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                // Si la cedula existe se busca la llave para identificar la ubicacion de la cedula y si ha votado o no usando el valor de campo votado
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    String uid = childSnapshot.getKey();
                                    Log.d("Firebase database", "Key is: " + uid);
                                    Boolean votado = childSnapshot.child("Votado").getValue(Boolean.class);
                                    // Intent para ir a la pantalla de votacion y enviar el uid del usuario si no ha votado. De lo contrario va a la pantalla de los resultados
                                    Intent VotacionIntent = new Intent(getApplicationContext(), Votacion.class);
                                    Intent ResultadosIntent = new Intent(getApplicationContext(), Votos.class);
                                    VotacionIntent.putExtra("uid", uid);
                                    if (Boolean.TRUE.equals(votado)) {
                                        startActivity(ResultadosIntent);
                                    } else {
                                        startActivity(VotacionIntent);
                                    }
                                }
                            } else {
                                // Si no existe la cedula se muestra en log de debug y en un toast
                                Toast.makeText(getApplicationContext(), "Cédula no encontrada", Toast.LENGTH_LONG).show();
                                Log.d("Firebase database", "No existe");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Si existe algun error con la base de datos al leer la cedula se muestra un warning en log
                            Log.w("Firebase database", "Failed to read value.", error.toException());
                        }
                    });
                } else {
                    // Si la cedula no coincide con el formato se muestra un toast
                    Toast.makeText(getApplicationContext(), "Debe ingresar una cédula válida",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}