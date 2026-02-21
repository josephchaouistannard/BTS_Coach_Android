package com.example.coach.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;
import com.google.gson.Gson;

import java.util.Date;

public class CalculPresenter {
    private ICalculView vue;
    private static final String NOM_FIC = "coach_fic";
    private static final String PROFIL_CLE = "profil_json";
    private SharedPreferences prefs;
    private Gson gson;

    /**
     * Constructeur de classe
     * @param vue
     */
    public CalculPresenter(ICalculView vue, Context context) {
        this.vue = vue;
        this.prefs = context.getSharedPreferences(NOM_FIC, Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    private void sauvegarderProfil(Profil profil) {
        String json = gson.toJson(profil);
        prefs.edit().putString(PROFIL_CLE, json).apply();
    }

    /**
     * Crée le profil à partir des données saisies
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    public void creerProfil(Integer poids, Integer taille, Integer age, Integer sexe) {
        Profil profil = new Profil(poids, taille, age, sexe, new Date());
        this.sauvegarderProfil(profil);
        vue.afficherResultat(profil.getImage(), profil.getImg(), profil.getMessage(), profil.normal());
    }

    public void chargerProfil() {
        String json = prefs.getString(PROFIL_CLE, null);
        if (json != null) {
            Profil profil = gson.fromJson(json, Profil.class);
            vue.remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        }
    }
}
