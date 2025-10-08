package org.example.animals;

/**
 * Classe abstraite scellée représentant un animal participant à la course.
 * Cette classe définit le comportement commun et les propriétés partagées.
 * Seules les classes Tortue, Lapin et Cheval peuvent en hériter.
 */
public abstract sealed class Animal permits Tortue, Lapin, Cheval {
    private final String nom;
    private final String symbole;
    private int position;

    /**
     * Constructeur d’un animal avec son nom unique et son symbole pour l’affichage.
     * @param nom Nom de l’animal.
     * @param symbole Symbole Unicode représentant l’animal dans la console.
     */
    public Animal(String nom, String symbole) {
        this.nom = nom;
        this.symbole = symbole;
        this.position = 0;
    }

    public String getNom() {
        return nom;
    }

    public String getSymbole() {
        return symbole;
    }

    public synchronized int getPosition() {
        return position;
    }

    public synchronized void avancer(int pas, int distanceMax) {
        position += pas;
        if (position > distanceMax) position = distanceMax;
    }

    public synchronized void resetPosition() {
        position = 0;
    }

    public abstract void crier();
}
