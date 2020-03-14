package me.mmigas.mines;

import me.mmigas.AutoMines;
import me.mmigas.events.MineCreateEvent;
import me.mmigas.math.BlockVector3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineController {

    private List<Mine> mines = new ArrayList<>();

    private List<Mine> resetQueue = new ArrayList<>();

    /**
     * Start the resetTimerVerification task
     */
    public MineController() {
        resetVerificationController();
        resetController();
    }

    public void createMine(String name) {
        mines.add(new Mine(name));
    }

    public void createMine(String name, World world, BlockVector3D minPosition, BlockVector3D maxPosition) {
        Mine mine = new Mine(name, world, minPosition, maxPosition);
        MineCreateEvent event = new MineCreateEvent(mine);
        Bukkit.getServer().getPluginManager().callEvent(event);
        mines.add(mine);
    }

    public void deleteMine(Mine mine) {
        mines.remove(mine);
    }

    public void setMineArea(@NotNull Mine mine, BlockVector3D minPosition, BlockVector3D maxPosition) {
        mine.setMinPosition(minPosition);
        mine.setMaxPosition(maxPosition);
        mine.calculatedDimensions();
    }

    public boolean containsMateiral(@NotNull Mine mine, Material material) {
        for(int i = 0; i < mine.getContent().size(); i++) {
            if(mine.getContent().get(i).getMaterial() == material)
                return true;
        }
        return false;
    }

    public boolean addBlock(@NotNull Mine mine, Material material, int percentage) {
        if(percentage + mine.getTotal() > 100) {
            return false;
        }

        if(!material.isBlock() || !material.isSolid()){
            return false;
        }

        mine.addTotal(percentage);
        mine.getContent().add(new Container(material, mine.getTotal()));
        Collections.sort(mine.getContent());
        return true;
    }

    public boolean changeBlock(@NotNull Mine mine, Material material, int percentage) {
        if(percentage + mine.getTotal() > 100) {
            return false;
        }
        removeBlock(mine, material);
        addBlock(mine, material, percentage);
        return true;
    }

    public void removeBlock(Mine mine, Material material) {
        int materialIndex = getContainerIdByMaterial(mine, material);
        int percentage;

        if(materialIndex == 0) {
            percentage = mine.getContent().get(materialIndex).getPercentage();
        } else {
            percentage = mine.getContent().get(materialIndex).getPercentage() - mine.getContent().get(materialIndex - 1).getPercentage();
        }

        if(materialIndex != mine.getContent().size()) {
            for(int i = materialIndex + 1; i < mine.getContent().size(); i++) {
                mine.getContent().get(i).removePercentage(percentage);
            }
        }


        mine.removeTotal(percentage);
        mine.getContent().remove(materialIndex);
    }

    /**
     * Used to calculate the mined percentage
     * The calculations are done when one block as been mined from the mine
     * then removes 1 to the area integer and divides by its dimentions
     * if the mined percentage is lower then the reset percentage the mine resets
     */
    public void updateMinedPercentage(@NotNull Mine mine) {
        mine.setMinedPercentage((mine.getArea() - 1) / mine.getArea());
        if(mine.getResetPercentage() <= mine.getMinedPercentage())
            mineResetRequest(mine);
    }

    private int getContainerIdByMaterial(@NotNull Mine mine, Material material) {
        for(int i = 0; i < mine.getContent().size(); i++) {
            if(mine.getContent().get(i).getMaterial() == material) {
                return i;
            }
        }
        throw new IllegalStateException("Something went really wrong.");
    }

    public void setTeleportLocation(@NotNull Mine mine, Location location) {
        mine.setTeleportLocation(location);
        mine.getFlags().add(Flags.TeleportLocation);
    }

    public void setResetPercentage(@NotNull Mine mine, int percentage) {
        mine.setResetPercentage(percentage);
        if(!mine.getFlags().contains(Flags.PercentageReset)) {
            mine.getFlags().add(Flags.PercentageReset);
        }
    }

    public void mineResetRequest(Mine mine) {
        resetQueue.add(mine);
    }

    private void resetController() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Mine mine : resetQueue) {
                    mine.reset();
                    mine.updateResetTime();
                }
                resetQueue.clear();
            }
        }.runTaskTimer(AutoMines.getInstance(), 20, 20);
    }

    /**
     * Controller task that verifies which mine needs to reset in that tick
     */
    private void resetVerificationController() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Mine mine : mines) {
                    if(mine.getFlags().contains(Flags.TimeReset) && mine.getResetTime() < System.currentTimeMillis()) {
                        mineResetRequest(mine);
                    }
                }
            }
        }.runTaskTimer(AutoMines.getInstance(), 0, 20);
    }

    /**
     * Sets up the mine's reset cooldown
     *
     * @param mine     thats getting updated
     * @param cooldown new cooldown
     * @return false if the cooldown is lower than 1 second
     */
    public boolean setResetTimer(Mine mine, long cooldown) {
        if(cooldown <= 1) {
            return false;
        }

        mine.setResetCooldown(cooldown);

        if(!mine.getFlags().contains(Flags.TimeReset)) {
            mine.getFlags().add(Flags.TimeReset);
        }
        return true;
    }

    public void startMineResetTimer(Mine mine) {
        mine.getFlags().add(Flags.TimeReset);
        mine.updateResetTime();
    }

    public void stopMineResetTimer(Mine mine) {
        mine.getFlags().remove(Flags.TimeReset);
    }

    public List<Mine> getMinesList() {
        return mines;
    }

    public Mine getMine(String name) {
        for(Mine m : mines) {
            if(m.getName().equals(name)) {
                return m;
            }
        }
        throw new IllegalStateException("Something went really wrong.");
    }

    public boolean validateMine(String name) {
        for(Mine m : mines) {
            if(m.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

}
