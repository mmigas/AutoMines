package me.mmigas.mines;

import com.sk89q.worldedit.math.BlockVector3;
import me.mmigas.AutoMines;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class Mine {
    private String name;
    private BlockVector3 minPosition, maxPosition;
    private World world;

    private List<Conteiner> conteiners = new ArrayList<>();
    private int total = 0;

    private ArrayList<Flags> flags = new ArrayList<>();

    private int length = 0;
    private int width = 0;
    private int height = 0;
    private int area = 0;


    Mine(String name) {
        this.name = name;
    }

    Mine(String name, World world, BlockVector3 minPosition, BlockVector3 maxPosition) {
        this.name = name;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.world = world;
        calculatedDimensions();
    }

    void reset() {
        ArrayList<Material> materialsGenerated = generatedMaterialsList();
        int counter = 0;
        for (int z = 0; z < length; z++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int finalX = minPosition.getX() + x;
                    int finalY = minPosition.getY() + y;
                    int finalZ = minPosition.getZ() + z;

                    world.getBlockAt(new Location(world, (double) finalX, (double) finalY, (double) finalZ)).setType(materialsGenerated.get(counter));
                    counter++;
                }
            }
        }
    }

    private ArrayList<Material> generatedMaterialsList() {
        ArrayList<Material> materials = new ArrayList<>();

        Random random = new Random();
        Material materialGenerated;
        nextBlock:
        for (int i = 0; i < area; i++) {
            int probabilityGenerated = random.nextInt(100);

            for (Conteiner conteiner : conteiners) {
                if (probabilityGenerated < conteiner.getPercentage()) {
                    materialGenerated = conteiner.getMaterial();
                    materials.add(conteiner.getMaterial());
                    AutoMines.getInstance().getLogger().info(probabilityGenerated + " " + materialGenerated);
                    continue nextBlock;
                }
            }
            materialGenerated = Material.AIR;
            materials.add(Material.AIR);
            AutoMines.getInstance().getLogger().info(probabilityGenerated + " " + materialGenerated);
        }

        return materials;
    }

    private BukkitRunnable resetTask;

    void startTimerCooldown(int periodInSec) {
        resetTask = (BukkitRunnable) new BukkitRunnable() {
            @Override
            public void run() {
                reset();
            }
        }.runTaskTimerAsynchronously(AutoMines.getInstance(), 0, periodInSec);
    }

    void stopTimerCooldown() {
        Bukkit.getScheduler().cancelTask(resetTask.getTaskId());
    }

    void calculatedDimensions() {
        width = maxPosition.getBlockX() - minPosition.getBlockX() + 1;
        height = maxPosition.getBlockY() - minPosition.getBlockY() + 1;
        length = maxPosition.getBlockZ() - minPosition.getBlockZ() + 1;
        area = (int) (Math.sqrt(Math.pow(Math.sqrt(Math.pow(width, 2) * Math.pow(height, 2)), 2) * Math.pow(length, 2)));
    }

    String getName() {
        return name;
    }

    List<Conteiner> getContent() {
        return conteiners;
    }

    ArrayList<Flags> getFlags() {
        return flags;
    }

    void setMinPosition(BlockVector3 minPosition) {
        this.minPosition = minPosition;
    }

    void setMaxPosition(BlockVector3 maxPosition) {
        this.maxPosition = maxPosition;
    }

    BlockVector3 getMinPosition() {
        return minPosition;
    }

    BlockVector3 getMaxPosition() {
        return maxPosition;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    int getLength() {
        return length;
    }

    int getArea() {
        return area;
    }

    int getTotal() {
        return total;
    }

    void addTotal(int amount) {
        total += amount;
    }

    void removeTotal(int amount) {
        total -= amount;
    }

    int getPercentageFromBlock(Material material) {
        for (Conteiner conteiner : conteiners) {
            if (conteiner.getMaterial().equals(material))
                return conteiner.getPercentage();
        }
        throw new IllegalStateException();
    }

}

