package me.mmigas.mines;

import org.bukkit.Material;

class Conteiner implements Comparable<Conteiner> {

    private Material material;
    private int percentage;

    Conteiner(Material material, int percentage) {
        this.material = material;
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Conteiner other) {
        return Integer.compare(percentage, other.percentage);
    }

    Material getMaterial() {
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

    int getPercentage() {
        return percentage;
    }

}


