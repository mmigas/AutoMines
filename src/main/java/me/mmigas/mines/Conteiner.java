package me.mmigas.mines;

import org.bukkit.Material;

public class Conteiner implements Comparable<Conteiner> {

    private Material material;
    private int percentage;

    public Conteiner(Material material, int percentage) {
        this.material = material;
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Conteiner other) {
        return Integer.compare(percentage, other.percentage);
    }

    public Material getMaterial() {
        return material;
    }

    void setPercentage(int amount){
        percentage = amount;
    }

    void addPercentage(int  amount) {
        percentage += amount;
    }

    void removePercentage(int amount) {
        percentage -= amount;
    }

    public int getPercentage() {
        return percentage;
    }

}


