package org.example.animals;

public final class Lapin extends Animal {
    public Lapin() {
        super("Lapin", "🐇");
    }
    @Override
    public void crier() {
        System.out.println("Lapin: Vite, vite, je suis rapide !");
    }
}
