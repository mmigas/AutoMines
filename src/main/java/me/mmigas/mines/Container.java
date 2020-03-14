package me.mmigas.mines;

import org.bukkit.Material;

public class Container implements Comparable<Container> {

    private Material material;
    private int percentage;

    public Container(Material material, int percentage) {
        this.material = material;
        this.percentage = percentage;
    }

    @Override
    public int compareTo(Container other) {
        return Integer.compare(percentage, other.percentage);
    }

    public Material getMaterial() {
        return material;
    }

    void setPercentage(int amount) {
        percentage = amount;
    }

    void addPercentage(int amount) {
        percentage += amount;
    }

    void removePercentage(int amount) {
        percentage -= amount;
    }

    public int getPercentage() {
        return percentage;
    }

}


