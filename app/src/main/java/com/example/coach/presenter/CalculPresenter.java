package com.example.coach.presenter;

import android.util.Log;

import androidx.appcompat.widget.ThemedSpinnerAdapter;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.ICalculView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalculPresenter {
    private ICalculView vue;

    /**
     * Constructeur de classe
     * @param vue
     */
    public CalculPresenter(ICalculView vue) {
        this.vue = vue;
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
        vue.afficherResultat(
                profil.getImage(),
                profil.getImg(),
                profil.getMessage(),
                profil.normal()
        );
        String profilJson = CoachApi.getGson().toJson(profil);
        HelperApi.call(HelperApi.getApi().creerProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    vue.afficherMessage("profil enregistré");
                }else{
                    vue.afficherMessage("échec enregistrement profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec enregistrement profil");
            }
        });
    }

    public void chargerDernierProfil() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if(result != null){
                    List<Profil> profils = result;
                    if (profils != null && !profils.isEmpty()) {
                        // récupérer le plus récent
                        Profil dernier = Collections.max(profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );
                        vue.remplirChamps(dernier.getPoids(), dernier.getTaille(),
                                dernier.getAge(), dernier.getSexe());
                    } else {
                        vue.afficherMessage("échec récuperation dernier profil");
                    }
                } else {
                    vue.afficherMessage("échec récuperation dernier profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec récuperation dernier profil");
            }
        });

    }
}
