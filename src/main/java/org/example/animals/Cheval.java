package org.example.animals;

public final class Cheval extends Animal {
    public Cheval() {
        super("Cheval", "🐎");
    }

    @Override
    public void crier() {
        System.out.println("Cheval : Désolé mais vous avez perdu d'avance");
    }
}
