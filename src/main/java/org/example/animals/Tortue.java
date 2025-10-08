package org.example.animals;

public final class Tortue extends Animal {
    public Tortue() {
        super("Tortue", "🐢");
    }

    @Override
    public void crier() {
        System.out.println("Tortue: Je suis lente mais vous allez avoir une surprise !");
    }
}
