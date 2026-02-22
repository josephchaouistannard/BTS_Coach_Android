package com.example.coach.contract;

import com.example.coach.model.Profil;

import java.util.List;

public interface IHistoView extends IAllView {
    void afficherListe(List profils);
    void transfertProfil(Profil profil);
}
