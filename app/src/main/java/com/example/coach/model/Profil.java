package com.example.coach.model;

public class Profil {
    private static final int MIN_FEMME = 25;
    private static final int MAX_FEMME = 30;
    private static final int MIN_HOMME = 15;
    private static final int MAX_HOMME = 20;
    private static final String[] MESSAGE = {"trop faible", "normal", "trop élevé"};
    public String getMessage() {
        return MESSAGE[this.indice];
    }
    private static final String[] IMAGE = {"maigre", "normal", "graisse"};
    private Integer poids;
    private Integer taille;
    private Integer age;
    private Integer sexe;

    public double getImg() {
        return img;
    }
    private double img;

    public String getImage() {
        return IMAGE[this.indice];
    }

    private int indice;

    public Profil(Integer poids, Integer taille, Integer age, Integer sexe) {
        this.poids = poids;
        this.taille = taille;
        this.age = age;
        this.sexe = sexe;
        this.img = calculImg();
        this.indice = calculIndice();
    }

    private double calculImg() {
        double tailleM = taille / 100.0;
        double res = (1.2 * poids/(tailleM*tailleM)) + (0.23 * age) - (10.83 * sexe) - 5.4;
        return res;
    }

    private int calculIndice() {
        int min = MIN_HOMME;
        int max = MAX_HOMME;
        if (this.sexe == 0) {
            min = MIN_FEMME;
            max = MAX_FEMME;
        }
        if (this.img < min) {
            return 0;
        } else if (this.img < max) {
            return 1;
        }
        return 2;
    }

    public boolean normal() {
        return this.indice == 1;
    }

}
