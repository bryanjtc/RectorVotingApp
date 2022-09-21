package com.example.hdp4proyecto1;

import android.os.Bundle;
import android.util.Log;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_votos);
        double totalVotos, porcentajeVivian, porcentajeMartin, porcentajeOmar;
        // Todo inicializar seekbar y button con los id del layout

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
        totalVotos = votosVivian + votosMartin + votosOmar;
        porcentajeVivian = (votosVivian/totalVotos)*100;
        porcentajeMartin = (votosMartin/totalVotos)*100;
        porcentajeOmar = (votosOmar/totalVotos)*100;
        sbVivian.setProgress((int) porcentajeVivian);
        sbMartin.setProgress((int) porcentajeMartin);
        sbOmar.setProgress((int) porcentajeOmar);
        tvVivian.setText(String.format(Locale.getDefault(), "%.0f%%",porcentajeVivian));
        tvMartin.setText(String.format(Locale.getDefault(),"%.0f%%",porcentajeMartin));
        tvOmar.setText(String.format(Locale.getDefault(),"%.0f%%",porcentajeOmar));
    }
}