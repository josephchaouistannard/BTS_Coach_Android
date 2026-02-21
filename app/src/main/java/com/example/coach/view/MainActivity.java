package com.example.coach.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.presenter.CalculPresenter;

public class MainActivity extends AppCompatActivity implements ICalculView {
    private EditText txtPoids;
    private EditText txtTaille;
    private EditText txtAge;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblImg;
    private ImageView imgSmiley;
    private Button btnCalc;
    private CalculPresenter presenter;

    /**
     * Charge les objets graphiques
     */
    private void chargeObjetsGraphiques() {
        txtPoids = (EditText) findViewById(R.id.txtPoids);
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        txtAge = (EditText) findViewById(R.id.txtAge);
        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        rdFemme = (RadioButton) findViewById(R.id.rdFemme);
        lblImg = (TextView) findViewById(R.id.lblResultat);
        imgSmiley = (ImageView) findViewById(R.id.imgSmiley);
        btnCalc = (Button) findViewById(R.id.btnCalc);
    }

    private void init() {
        presenter = new CalculPresenter(this, this);
        chargeObjetsGraphiques();
        btnCalc.setOnClickListener(v -> btnCalc_clic());
        presenter.chargerProfil();

    }

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

    /**
     * Affiche resultat de IMG
     * @param image
     * @param img
     * @param message
     * @param normal
     */
    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (imageId == 0) {
            imgSmiley.setImageResource(R.drawable.normal);
        } else {
            imgSmiley.setImageResource(imageId);
        }

        String texte = String.format("%.01f", img) + " : IMG " + message;
        lblImg.setText(texte);
        if (normal) {
            lblImg.setTextColor(Color.GREEN);
        } else {
            lblImg.setTextColor(Color.RED);
        }

    }

    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        txtPoids.setText(poids.toString());
        txtTaille.setText(taille.toString());
        txtAge.setText(age.toString());
        if (sexe == 1) {
            rdHomme.setChecked(true);
        } else {
            rdFemme.setChecked(true);
        }
    }

    private void btnCalc_clic() {
        Integer poids = 0;
        Integer age = 0;
        Integer taille = 0;
        Integer sexe = 0;
        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
        } catch (Exception ignored) {}
        if (rdHomme.isChecked()) {
            sexe = 1;
        }
        if (poids == 0 || age == 0 || taille == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            presenter.creerProfil(poids, taille, age, sexe);
        }


    }
}