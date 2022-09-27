package com.example.hdp4proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class Votos extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("data");
    Long votosVivian = 0L, votosMartin = 0L, votosOmar = 0L, votosNA;
    ProgressBar pbVivian, pbMartin, pbOmar, pbNA;
    TextView tvVivian, tvMartin, tvOmar, tvNA;
    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votos);
        pbVivian = findViewById(R.id.progress_bar1);
        pbMartin = findViewById(R.id.progress_bar2);
        pbOmar = findViewById(R.id.progress_bar3);
        pbNA = findViewById(R.id.progress_bar4);
        tvVivian = findViewById(R.id.tv_percent1);
        tvMartin = findViewById(R.id.tv_percent2);
        tvOmar = findViewById(R.id.tv_percent3);
        tvNA = findViewById(R.id.tv_percent4);
        btnRegresar = findViewById(R.id.btn_salir);
        // Se obtienen la data de la base de datos donde el campo voto es igual a los nombres de los candidatos
        Query queryVivian = myRef.orderByChild("Voto").equalTo("Vivían Valenzuela");
        Query queryMartin = myRef.orderByChild("Voto").equalTo("Martín Candanedo");
        Query queryOmar = myRef.orderByChild("Voto").equalTo("Omar Aizpurua");
        Query queryNA = myRef.orderByChild("Voto").equalTo("Ninguno");
        queryVivian.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se cuenta la cantidad de veces donde el valor del campo voto de la base de datos es Vivian Valenzuela y se guarda el valor
                Log.i("Votos", "Conteo para Vivian: " + snapshot.getChildrenCount());
                votosVivian = snapshot.getChildrenCount();
                Query totalVotos = myRef.orderByChild("Votado").equalTo(true);
                totalVotos.addValueEventListener(new ValueEventListener() {
                    // Se calcula el porcentaje de votos usando el total y la cantidad de votos
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long total = snapshot.getChildrenCount();
                        double porcentajeVivian = ((double) votosVivian / total) * 100;
                        pbVivian.setProgress((int) porcentajeVivian);
                        tvVivian.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeVivian));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Si existe algun error con la base de datos al leer los estudiantes que votaron se muestra un warning en log
                        Log.w("Firebase database", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si existe algun error con la base de datos al leer el nombre del candidato se muestra un warning en log
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        queryMartin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se cuenta la cantidad de veces donde el valor del campo voto de la base de datos es Martin Candanedo y se guarda el valor
                Log.i("Votos", "Conteo para Martian: " + snapshot.getChildrenCount());
                votosMartin = snapshot.getChildrenCount();
                Query totalVotos = myRef.orderByChild("Votado").equalTo(true);
                totalVotos.addValueEventListener(new ValueEventListener() {
                    // Se calcula el porcentaje de votos usando el total y la cantidad de votos
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long total = snapshot.getChildrenCount();
                        double porcentajeMartin = ((double) votosMartin / total) * 100;
                        pbMartin.setProgress((int) porcentajeMartin);
                        tvMartin.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeMartin));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Si existe algun error con la base de datos al leer los estudiantes que votaron se muestra un warning en log
                        Log.w("Firebase database", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si existe algun error con la base de datos al leer el nombre del candidato se muestra un warning en log
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        queryOmar.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se cuenta la cantidad de veces donde el valor del campo voto de la base de datos es Omar Aizpura y se guarda el valor
                Log.i("Votos", "Conteo para Omar: " + snapshot.getChildrenCount());
                votosOmar = snapshot.getChildrenCount();
                Query totalVotos = myRef.orderByChild("Votado").equalTo(true);
                totalVotos.addValueEventListener(new ValueEventListener() {
                    // Se calcula el porcentaje de votos usando el total y la cantidad de votos
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long total = snapshot.getChildrenCount();
                        double porcentajeOmar = ((double) votosOmar / total) * 100;
                        pbOmar.setProgress((int) porcentajeOmar);
                        tvOmar.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeOmar));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Si existe algun error con la base de datos al leer los estudiantes que votaron se muestra un warning en log
                        Log.w("Firebase database", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si existe algun error con la base de datos al leer el nombre del candidato se muestra un warning en log
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        queryNA.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se cuenta la cantidad de veces donde el valor del campo voto de la base de datos es Ninguno y se guarda el valor
                Log.i("Votos", "Conteo para Ninguno: " + snapshot.getChildrenCount());
                votosNA = snapshot.getChildrenCount();
                Query totalVotos = myRef.orderByChild("Votado").equalTo(true);
                totalVotos.addValueEventListener(new ValueEventListener() {
                    // Se calcula el porcentaje de votos usando el total y la cantidad de votos
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long total = snapshot.getChildrenCount();
                        double porcentajeNA = ((double) votosNA / total) * 100;
                        pbNA.setProgress((int) porcentajeNA);
                        tvNA.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeNA));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Si existe algun error con la base de datos al leer los estudiantes que votaron se muestra un warning en log
                        Log.w("Firebase database", "Failed to read value.", error.toException());
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si existe algun error con la base de datos al leer el nombre del candidato se muestra un warning en log
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        // Intent para regresar a la pantall inicial
        btnRegresar.setOnClickListener(view -> {
            Intent PantallaInicialIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(PantallaInicialIntent);
        });
    }
}