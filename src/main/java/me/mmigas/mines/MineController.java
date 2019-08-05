package me.mmigas.mines;

import com.sk89q.worldedit.math.BlockVector3;
import me.mmigas.events.MineResetEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.*;

public class MineController {

    private List<Mine> mines = new ArrayList<>();

    public void createMine(String name) {
        mines.add(new Mine(name));
    }

    public void createMine(String name, World world, BlockVector3 minPosition, BlockVector3 maxPosition) {
        mines.add(new Mine(name, world, minPosition, maxPosition));
    }

    public void deleteMine(Mine mine) {
        mines.remove(mine);
    }

    public void setMineArea(Mine mine, BlockVector3 minPosition, BlockVector3 maxPosition) {
        mine.setMinPosition(minPosition);
        mine.setMaxPosition(minPosition);
        mine.calculatedDimensions();
    }

    public boolean containsBlock(Mine mine, Material material) {
        for (int i = 0; i < mine.getContent().size(); i++) {
            if (mine.getContent().get(i).getMaterial() == material)
                return true;
        }
        return false;
    }

    public boolean addBlock(Mine mine, Material material, int percentage) {
        if (percentage + mine.getTotal() > 100) {
            return false;
        }

        mine.addTotal(percentage);
        mine.getContent().add(new Conteiner(material, mine.getTotal()));
        Collections.sort(mine.getContent());
        return true;
    }

    public boolean changeBlock(Mine mine, Material material, int percentage) {
        if (percentage + mine.getTotal() > 100) {
            return false;
        }

        int materialIndex = getContainerIdByMaterial(mine, material);
        boolean add = mine.getContent().get(materialIndex).getPercentage() < percentage;
        mine.removeTotal(mine.getContent().get(materialIndex).getPercentage());

        if (materialIndex == 0) {
            mine.getContent().get(materialIndex).setPercentage(percentage);
        } else {
            mine.getContent().get(materialIndex).setPercentage(mine.getContent().get(materialIndex - 1).getPercentage() + percentage);
        }

        if (add) {
            for (int i = mine.getContent().size() - 1; i > materialIndex; i--) {
                int newPercentage = mine.getContent().get(i).getPercentage() - percentage;
                mine.getContent().get(i).addPercentage(newPercentage);
            }
        } else {
            for (int i = mine.getContent().size() - 1; i > materialIndex; i--) {
                int newPercenteage = mine.getContent().get(i).getPercentage() - percentage;
                mine.getContent().get(i).removePercentage(newPercenteage);
            }
        }


        mine.addTotal(percentage);
        return true;
    }

    public void removeBlock(Mine mine, Material material) {
        int materialIndex = getContainerIdByMaterial(mine, material);
        int percentage;

        if (materialIndex == 0) {
            percentage = mine.getContent().get(materialIndex).getPercentage();
        } else {
            percentage = mine.getContent().get(materialIndex).getPercentage() - mine.getContent().get(materialIndex - 1).getPercentage();
        }

        if (materialIndex != mine.getContent().size()) {
            for (int i = materialIndex + 1; i < mine.getContent().size(); i++) {
                mine.getContent().get(i).removePercentage(percentage);
            }
        }

        mine.removeTotal(percentage);
        mine.getContent().remove(materialIndex);
    }

    public void resetMine(Mine mine) {
        mine.reset();
        MineResetEvent event = new MineResetEvent(mine);
        Bukkit.getPluginManager().callEvent(event);
    }

    public void startResetTimer(Mine mine, int time) {
        mine.getFlags().add(Flags.TimeReset);
        mine.startTimerCooldown(time);
    }

    public void stopResetTimer(Mine mine) {
        mine.getFlags().remove(Flags.TimeReset);
        mine.stopTimerCooldown();
    }

    public Mine getMine(String name) {
        for (Mine m : mines) {
            if (m.getName().equals(name)) {
                return m;
            }
        }
        throw new IllegalStateException("Something went really wrong.");
    }

    public boolean validateMine(String name) {
        for (Mine m : mines) {
            if (m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public String getBlockList(Mine mine) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < mine.getContent().size(); i++) {
            builder.append("- ").append(mine.getContent().get(i).getMaterial()).append(" ").append(mine.getContent().get(i).getPercentage()).append("\n");
        }
        return builder.toString();
    }

    public String getDimensions(Mine mine) {
        StringBuilder builder = new StringBuilder();

        builder.append("MinPosition: ").append(mine.getMinPosition()).append("\n")
                .append("MaxPosition: ").append(mine.getMaxPosition()).append("\n")
                .append("Width: ").append(mine.getWidth()).append("\n")
                .append("Height: ").append(mine.getHeight()).append("\n")
                .append("Length: ").append(mine.getLength()).append("\n")
                .append("Area: ").append(mine.getArea()).append("\n");

        return builder.toString();
    }


    private int getContainerIdByMaterial(Mine mine, Material material) {
        for (int i = 0; i < mine.getContent().size(); i++) {
            if (mine.getContent().get(i).getMaterial() == material) {
                return i;
            }
        }
        throw new IllegalStateException("Something went really wrong.");
    }
}
