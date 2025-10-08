package org.example;

import org.example.animals.Animal;
import org.example.animals.Cheval;
import org.example.animals.Lapin;
import org.example.animals.Tortue;
import org.example.controllers.CourseController;
import org.example.threads.AnimalThread;
import org.example.threads.ThreadArbitre;

import java.util.Arrays;
import java.util.List;
import java.util.*;

/**
 * Classe principale qui lance la simulation d'une course entre plusieurs animaux,
 * chacun contrôlé par un thread indépendant, sur un nombre défini de manches.
 *
 * <p>Le programme simule la progression concurrente des animaux sur une distance donnée,
 * affiche la course en temps réel, gère la synchronisation pour désigner un seul vainqueur par manche,
 * et compile un classement global des victoires.</p>
 *
 * <p>Les constantes de configuration (distance de la course, nombre de manches) sont déclarées
 * en tant que constantes statiques finales pour plus de lisibilité et facilité de maintenance.</p>
 */
public class CourseAnimaux {
    /**
     * Il faut définir une distance totale à parcourir par chaque animal pour gagner une manche.
     */
    private static final int DISTANCE_COURSE = 30;
    /**
     * Il faut définir un nombre total de manches à disputer dans le jeu.
     */
    private static final int NB_MANCHES = 3;

    public static void main(String[] args) throws InterruptedException {

        System.out.println("\n########## Course du siècle ##########\n");

        Animal tortue = new Tortue();
        Animal lapin = new Lapin();
        Animal cheval = new Cheval();

        // Liste des animaux utiles pour initialisations et gestion collective
        List<Animal> animaux = Arrays.asList(tortue, lapin, cheval);
        // Map pour stocker et compter les victoires cumulées par chaque animal
        Map<String, Integer> victoiresParAnimal = new HashMap<>();

        // Initialisation des victoires : chaque animal débute à 0 victoire
        for (Animal a : animaux) {
            victoiresParAnimal.put(a.getNom(), 0);
        }

        // Expression du caractère propre à chaque animal avant le départ
        animaux.forEach(Animal::crier);

        for (int manche = 1; manche <= NB_MANCHES; manche++) {
            System.out.printf("\n======= Manche %d =======\n", manche);
            // Initialisation d'un nouveau contrôleur de course pour chaque manche
            CourseController controller = new CourseController(DISTANCE_COURSE);

            // Enregistrement des animaux au contrôleur pour gestion commune
            animaux.forEach(controller::enregistrerAnimal);

            // Réinitialisation des positions des animaux avant la manche
            animaux.forEach(Animal::resetPosition);

            // Liste tous les threads à lancer, ici l'index permet de définir la position de l'animal sur la ligne de départ
            // (0 → ... = haut vers le bas)
            List<AnimalThread> threads = Arrays.asList(
                    new AnimalThread(tortue, 0, controller),
                    new AnimalThread(lapin, 1, controller),
                    new AnimalThread(cheval, 2, controller)
            );

            // Démarrage du thread arbitre qui affiche régulièrement le classement
            ThreadArbitre arbitre = new ThreadArbitre(controller);
            arbitre.start();

            System.out.println("# A VOS MARQUES, PRÊTS !");
            // Affichage initial positions avant démarrage de la course
            threads.forEach(t -> controller.afficherPosition(t.getAnimal(), threads.indexOf(t)));

            System.out.println("\n\n# PARTEZ !");
            threads.forEach(Thread::start);

            // Boucle active avec pause pour attendre fin de la manche
            while (!controller.isCourseTerminee()) {
                Thread.sleep(400);
            }

            // Attendre que tous les threads s’arrêtent proprement
            for (AnimalThread t : threads) {
                t.join();
            }

            // Arrêt du thread arbitre après fin de manche
            arbitre.arreter();
            arbitre.join();

            // Affichage du classement final de la manche courante
            System.out.println("\n# Classement final manche " + manche + " :");
            List<Animal> finalClassement = controller.getClassement();
            for (Animal a : finalClassement) {
                System.out.printf("%s - distance parcourue : %d\n", a.getNom(), a.getPosition());
            }

            // Mise à jour des victoires selon vainqueur de la manche
            Animal gagnant = finalClassement.getFirst();
            victoiresParAnimal.put(gagnant.getNom(), victoiresParAnimal.get(gagnant.getNom()) + 1);
        }

        // Affichage du classement global des victoires avec tri décroissant
        System.out.println("\n############################################\n");
        System.out.println("\n=== Classement global des victoires ===");
        victoiresParAnimal.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue(Comparator.reverseOrder()))
                .forEach(entry -> System.out.printf("%s : %d victoire(s)\n", entry.getKey(), entry.getValue()));

        System.out.println("\n======= Fin du jeu =======");
    }
}
