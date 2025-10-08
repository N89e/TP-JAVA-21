# README

## Présentation

C'est une course entre trois animaux : une tortue, un lapin et un cheval. Chaque animal avance en même temps grâce à des threads, ce qui montre comment gérer plusieurs tâches en parallèle en Java.

## Pourquoi cette structure de code ?

- **Séparation claire des rôles** :
    - Les animaux sont définis dans leur propre classe pour gérer leur état et comportement.
    - Le contrôleur de course s'occupe de la logique de la course et de l'affichage.
    - Chaque animal a son propre thread pour simuler son parcours en parallèle.
    - Un thread arbitre affiche régulièrement le classement sans bloquer la course.


## Pour lancer l'application

### Avec IntelliJ IDEA

1. Ouvre IntelliJ et crée un nouveau projet Java.
2. Ajoute les fichiers sources dans la structure appropriée (`org.example` et sous-packages).
3. Assure-toi d'utiliser JDK 21 ou supérieur (pour les sealed classes).
4. Compile et exécute la classe `CourseAnimaux` en la sélectionnant puis en cliquant sur Run ou clique directement sur Run.

### Avec Visual Studio Code

1. Ouvre le dossier du projet dans VS Code.
2. Installe l'extension Java Extension Pack si ce n'est pas déjà fait.
3. Configure JDK 21 (ou supérieur) dans les paramètres.
4. Compile et lance la classe `CourseAnimaux` via le panneau Run or Debug ou en ligne de commande.

## Remarques

- La console doit supporter les codes ANSI pour afficher correctement la progression de la course.
- Tu peux modifier la distance ou le nombre de manches via les constantes dans la classe principale.
