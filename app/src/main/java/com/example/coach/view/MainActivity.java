package com.example.coach.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private ImageButton btnMonIMG;
    private ImageButton btnMonHistorique;

    private void chargerObjetsGraphiques() {
        btnMonIMG = (ImageButton) findViewById(R.id.btnMonIMG);
        btnMonHistorique = (ImageButton) findViewById(R.id.btnMonHistorique);
        creerMenu();
    }
    private void init() {
        chargerObjetsGraphiques();
    }

    private void ecouteMenu(Class classe) {
        Intent intent = new Intent(MainActivity.this, classe);
        startActivity(intent);
    }

    private void creerMenu() {
        btnMonIMG.setOnClickListener(v -> ecouteMenu(CalculActivity.class));
        btnMonHistorique.setOnClickListener(v -> ecouteMenu(HistoActivity.class));
    }
}