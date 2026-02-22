package com.example.coach.presenter;

import android.util.Log;

import com.example.coach.api.CoachApi;
import com.example.coach.api.HelperApi;
import com.example.coach.api.ICallbackApi;
import com.example.coach.api.IRequestApi;
import com.example.coach.api.ResponseApi;
import com.example.coach.contract.IHistoView;
import com.example.coach.model.Profil;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoPresenter {
    private IHistoView vue;

    public HistoPresenter(IHistoView vue) {
        this.vue = vue;
    }

    public void chargerProfils() {
        HelperApi.call(HelperApi.getApi().getProfils(), new ICallbackApi<List<Profil>>() {
            @Override
            public void onSuccess(List<Profil> result) {
                if (result != null) {
                    List<Profil> profils = result;
                    if (profils != null && !profils.isEmpty()) {
                        Collections.reverse(profils);
                        vue.afficherListe(profils);
                    } else {
                        vue.afficherMessage("échec récuperation profils");
                    }
                } else {
                    vue.afficherMessage("échec récuperation profils");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec récuperation profils");
            }
        });
    }

    public void transfertProfil(Profil profil) {
        vue.transfertProfil(profil);
    }
    public void supprProfil(Profil profil, ICallbackApi<Void> callback) {
        String profilJson = CoachApi.getGson().toJson(profil);
        HelperApi.call(HelperApi.getApi().supprProfil(profilJson), new ICallbackApi<Integer>() {
            @Override
            public void onSuccess(Integer result) {
                if (result == 1) {
                    callback.onSuccess(null);
                    vue.afficherMessage("profil supprimé");
                }else{
                    vue.afficherMessage("échec suppression profil");
                }
            }

            @Override
            public void onError() {
                vue.afficherMessage("échec suppression profil");
            }
        });
    }
}
