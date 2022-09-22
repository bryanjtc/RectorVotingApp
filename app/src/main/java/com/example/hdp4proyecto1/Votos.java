package com.example.hdp4proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
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
    Long votosVivian, votosMartin, votosOmar;
    SeekBar sbVivian, sbMartin, sbOmar;
    TextView tvVivian, tvMartin, tvOmar;
    Button btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votos);
        double totalVotos, porcentajeVivian, porcentajeMartin, porcentajeOmar;
        // Todo inicializar seekbar y button con los id del layout

        // Se obtienen la data de la base de datos donde el campo voto es igual a los nombres de los candidatos
        Query queryVivian = myRef.orderByChild("Voto").equalTo("Vivían Valenzuela");
        Query queryMartin = myRef.orderByChild("Voto").equalTo("Martín Candanedo");
        Query queryOmar = myRef.orderByChild("Voto").equalTo("Omar Aizpurua");
        queryVivian.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Se cuenta la cantidad de veces donde el valor del campo voto de la base de datos es Vivian Valenzuela y se guarda el valor
                Log.i("Votos", "Conteo para Vivian: " + snapshot.getChildrenCount());
                votosVivian = snapshot.getChildrenCount();
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Si existe algun error con la base de datos al leer el nombre del candidato se muestra un warning en log
                Log.w("Firebase database", "Failed to read value.", error.toException());
            }
        });
        // Se calculan la cantidad de votos total, el porcentaje de votos para cada candidatos y se guardan en el seekbar y el textView perteneciente a cada candidato
        totalVotos = votosVivian + votosMartin + votosOmar;
        porcentajeVivian = (votosVivian / totalVotos) * 100;
        porcentajeMartin = (votosMartin / totalVotos) * 100;
        porcentajeOmar = (votosOmar / totalVotos) * 100;
        sbVivian.setProgress((int) porcentajeVivian);
        sbMartin.setProgress((int) porcentajeMartin);
        sbOmar.setProgress((int) porcentajeOmar);
        tvVivian.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeVivian));
        tvMartin.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeMartin));
        tvOmar.setText(String.format(Locale.getDefault(), "%.0f%%", porcentajeOmar));
        // Intent para regresar a la pantall inicial
        btnRegresar.setOnClickListener(view -> {
            Intent PantallaInicialIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(PantallaInicialIntent);
        });
    }
}