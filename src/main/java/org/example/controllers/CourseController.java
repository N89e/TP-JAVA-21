package org.example.controllers;

import org.example.animals.Animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur principal de la course.
 * Gère la synchronisation des threads animaux et la logique de la course.
 * Fournit des méthodes pour afficher la progression, déclarer la victoire, et récupérer le classement.
 */
public class CourseController {
    private final int distanceCourse;
    private volatile boolean courseTerminee = false;
    private final Object verrouVictoire = new Object();
    private final List<Animal> classement = new ArrayList<>();
    private final List<Animal> animauxParticipants = new ArrayList<>();

    public CourseController(int distanceCourse) {
        this.distanceCourse = distanceCourse;
    }

    public boolean isCourseTerminee() {
        return courseTerminee;
    }

    public int getDistanceCourse() {
        return distanceCourse;
    }

    /**
     * Ajoute un animal à la liste des participants suivis par le contrôleur.
     * Synchronisé pour assurer la cohérence en environnement concurrent.
     * @param animal Animal à enregistrer.
     */
    public void enregistrerAnimal(Animal animal) {
        synchronized (animauxParticipants) {
            animauxParticipants.add(animal);
        }
    }

    /**
     * Affiche la progression d’un animal sur une ligne spécifique en console.
     * Utilise un index pour gérer la position verticale en console.
     * Synchronisé sur la sortie standard pour éviter mélange d’affichage.
     * @param animal Animal dont on veut afficher la position.
     * @param index Ligne d’affichage réservée à cet animal (0, 1, 2).
     */
    public void afficherPosition(Animal animal, int index) {
        synchronized (System.out) {
            if (index == 0) {
                System.out.print("\u001B[3A");
            }
            System.out.print("\r");
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-7s: ", animal.getNom()));
            for (int i = 0; i < distanceCourse; i++) {
                if (i == animal.getPosition()) {
                    sb.append(animal.getSymbole());
                } else {
                    sb.append('-');
                }
            }
            System.out.println(sb.toString());
            if (index == 2) {
                System.out.flush();
            }
        }
    }

    /**
     * Déclare la victoire d’un animal, en s’assurant qu’elle n’est attribuée qu’une fois.
     * Ajoute l’animal au classement final.
     * @param animal Animal vainqueur.
     */
    public void declarerVictoire(Animal animal) {
        synchronized (verrouVictoire) {
            if (!courseTerminee) {
                courseTerminee = true;
                classement.add(animal);
                System.out.printf("\n%s a gagné la course !\n\n", animal.getNom());
            } else {
                classement.add(animal);
            }
        }
    }

    /**
     * Renvoie la liste finale des animaux classés selon l’ordre d’arrivée (la course terminée).
     * @return Liste des animaux classés.
     */
    public List<Animal> getClassement() {
        synchronized (animauxParticipants) {
            return animauxParticipants.stream()
                    .sorted(Comparator.comparingInt(Animal::getPosition).reversed())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Renvoie le classement intermédiaire ordonné par la position,
     * utile pour l’affichage du thread arbitre avant la fin de la course.
     * @return Liste des animaux triés par distance décroissante parcourue.
     */
    public List<Animal> getClassementIntermediaire() {
        synchronized (animauxParticipants) {
            return animauxParticipants.stream()
                    .sorted(Comparator.comparingInt(Animal::getPosition).reversed())
                    .collect(Collectors.toList());
        }
    }
}

