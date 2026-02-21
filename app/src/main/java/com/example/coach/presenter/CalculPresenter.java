package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachApi;
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
        String profilJson = CoachApi.getGson().toJson(profil);
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);
        Call<ResponseApi<Integer>> call = api.creerProfil(profilJson);
        call.enqueue(new Callback<ResponseApi<Integer>>() {
            @Override
            public void onResponse(Call<ResponseApi<Integer>> call, Response<ResponseApi<Integer>> response) {
                Log.d("API", "code : " + response.body().getCode() +
                        " message : " + response.body().getMessage() +
                        " result : " + response.body().getResult()
                );
                if (response.isSuccessful() && response.body().getResult() == 1) {
                    vue.afficherResultat(
                            profil.getImage(),
                            profil.getImg(),
                            profil.getMessage(),
                            profil.normal()
                    );
                } else {
                    Log.e("API", "Erreur API: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<Integer>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });
    }

    public void chargerDernierProfil() {
        IRequestApi api = CoachApi.getRetrofit().create(IRequestApi.class);
        Call<ResponseApi<List<Profil>>> call = api.getProfils();
        call.enqueue(new Callback<ResponseApi<List<Profil>>>() {
            @Override
            public void onResponse(Call<ResponseApi<List<Profil>>> call, Response<ResponseApi<List<Profil>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Profil> profils = response.body().getResult();
                    if (profils != null && !profils.isEmpty()) {
                        Profil dernier = Collections.max(
                                profils,
                                (p1, p2) -> p1.getDateMesure().compareTo(p2.getDateMesure())
                        );
                        vue.remplirChamps(dernier.getPoids(), dernier.getTaille(), dernier.getAge(), dernier.getSexe());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseApi<List<Profil>>> call, Throwable throwable) {
                Log.e("API", "Échec d'accès à l'api", throwable);
            }
        });

    }
}
