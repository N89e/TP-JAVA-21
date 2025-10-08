package org.example.threads;

import org.example.animals.Animal;
import org.example.controllers.CourseController;

import java.util.Random;

/**
 * Thread dédié à l’animation d’un animal unique dans la course.
 * Il produit ses déplacements aléatoires et notifie sa progression au contrôleur.
 */
public class AnimalThread extends Thread {
    private final Animal animal;
    private final CourseController controller;
    private final int index;
    private final Random random = new Random();

    /**
     * Constructeur du thread animal.
     * @param animal Instance de l’animal à animer.
     * @param index Index qui détermine la ligne de départ dans la console.
     * @param controller Référence au contrôleur de course pour la coordination.
     */
    public AnimalThread(Animal animal, int index, CourseController controller) {
        super(animal.getNom());
        this.animal = animal;
        this.index = index;
        this.controller = controller;
    }

    /**
     * Fonction principale du thread qui fait avancer l’animal de manière aléatoire,
     * met à jour l’affichage et déclare la victoire si l’arrivée est franchie.
     */
    @Override
    public void run() {
        while (!controller.isCourseTerminee() && animal.getPosition() < controller.getDistanceCourse()) {
            int pas = 1 + random.nextInt(3);
            animal.avancer(pas, controller.getDistanceCourse());

            controller.afficherPosition(animal, index);

            if (animal.getPosition() >= controller.getDistanceCourse()) {
                controller.declarerVictoire(animal);
            }

            try {
                Thread.sleep(200 + random.nextInt(500));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Animal getAnimal() {
        return animal;
    }
}
