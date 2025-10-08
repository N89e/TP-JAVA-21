package org.example.threads;
import org.example.animals.Animal;
import org.example.controllers.CourseController;

import java.util.List;
/**
 * Thread arbitre chargé d'afficher régulièrement le classement intermédiaire
 * des animaux pendant la course, sans bloquer l'exécution des threads animaux.
 */
public class ThreadArbitre extends Thread {
    private final CourseController controller;
    private volatile boolean running = true;

    /**
     * Constructeur du thread arbitre.
     * @param controller Le contrôleur de la course pour récupérer les états.
     */
    public ThreadArbitre(CourseController controller) {
        this.controller = controller;
        setName("Arbitre");
    }

    public void arreter() {
        running = false;
        this.interrupt();
    }

    /**
     * Corps du thread. Affiche le classement intermittemment toutes les 500 ms
     * jusqu’à la fin de la course ou interruption.
     */
    @Override
    public void run() {
        while (running && !controller.isCourseTerminee()) {
            afficherClassementIntermediaire();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                break;
            }
        }
//        afficherClassementIntermediaire();
        System.out.println("# Arbitre : fin de la course.");
    }

    /**
     * Méthode interne qui affiche la liste ordonnée des animaux
     * selon leur position actuelle dans la course.
     */
    private void afficherClassementIntermediaire() {
        List<Animal> classement = controller.getClassementIntermediaire();
        System.out.println("\n# Classement intermédiaire :");
        for (Animal a : classement) {
            System.out.printf("%s - distance parcourue : %d\n", a.getNom(), a.getPosition());
        }
        System.out.println("\n");
    }
}

