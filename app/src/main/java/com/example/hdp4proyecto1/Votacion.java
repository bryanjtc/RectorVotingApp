package com.example.hdp4proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.atomic.AtomicReference;

public class Votacion extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");
    RadioButton rbCandidato1, rbCandidato2, rbCandidato3;
    Button btnVotar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votacion);
        String[] candidatos = new String[]{"Vivían Valenzuela", "Omar Aizpurua", "Martín Candanedo"};
        AtomicReference<Integer> seleccion = new AtomicReference<>(-1);
        Intent PantallaInicialIntent = getIntent();
        String uid = PantallaInicialIntent.getStringExtra("uid");
        btnVotar = findViewById(R.id.button);
        rbCandidato1 = findViewById(R.id.rbCandidato1);
        rbCandidato2 = findViewById(R.id.rbCandidato2);
        rbCandidato3 = findViewById(R.id.rbCandidato3);
        rbCandidato1.setOnClickListener(view -> seleccion.set(0));
        rbCandidato2.setOnClickListener(view -> seleccion.set(1));
        rbCandidato3.setOnClickListener(view -> seleccion.set(2));
        btnVotar.setOnClickListener(view -> {
            if (seleccion.get() != -1) {
                // Si el usuario ha seleccionado un candidato se guarda el valor en el campo voto de la base de datos y se cambia el valor del campo votado a true
                myRef.child(uid).child("Voto").setValue(candidatos[seleccion.get()]);
                myRef.child(uid).child("Votado").setValue(true);
                Log.d("Votacion", "Voto es: " + candidatos[seleccion.get()]);
                // Intent para ver la pantalla de los resultados
                Intent ResultadosIntent = new Intent(getApplicationContext(), Votos.class);
                startActivity(ResultadosIntent);
            } else {
                // Si el usuario no ha seleccionado a un candidato se muestra un toast
                Toast.makeText(getApplicationContext(), "Debe seleccionar un candidato",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}