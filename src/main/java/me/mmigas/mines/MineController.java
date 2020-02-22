package me.mmigas.mines;

import me.mmigas.AutoMines;
import me.mmigas.events.MineCreateEvent;
import me.mmigas.math.BlockVector3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineController {

    private List<Mine> mines = new ArrayList<>();

    private List<Mine> resetQueue = new ArrayList<>();
    //Mutex lock = new Mutex();

    /**
     * Start the resetTimerVerification task
     */
    public MineController() {
        resetVerificationController();
    }

    public void createMine(String name) {
        Mine mine = new Mine(name);
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

    public void setMineArea(Mine mine, BlockVector3D minPosition, BlockVector3D maxPosition) {
        mine.setMinPosition(minPosition);
        mine.setMaxPosition(maxPosition);
        mine.calculatedDimensions();
    }

    public boolean containsBlock(Mine mine, Material material) {
        for(int i = 0; i < mine.getContent().size(); i++) {
            if(mine.getContent().get(i).getMaterial() == material)
                return true;
        }
        return false;
    }

    public boolean addBlock(Mine mine, Material material, int percentage) {
        if(percentage + mine.getTotal() > 100) {
            return false;
        }

        mine.addTotal(percentage);
        mine.getContent().add(new Conteiner(material, mine.getTotal()));
        Collections.sort(mine.getContent());
        return true;
    }

    public boolean changeBlock(Mine mine, Material material, int percentage) {
        if(percentage + mine.getTotal() > 100) {
            return false;
        }

        int materialIndex = getContainerIdByMaterial(mine, material);
        boolean add = mine.getContent().get(materialIndex).getPercentage() < percentage;
        mine.removeTotal(mine.getContent().get(materialIndex).getPercentage());

        if(materialIndex == 0) {
            mine.getContent().get(materialIndex).setPercentage(percentage);
        } else {
            mine.getContent().get(materialIndex).setPercentage(mine.getContent().get(materialIndex - 1).getPercentage() + percentage);
        }

        if(add) {
            for(int i = mine.getContent().size() - 1; i > materialIndex; i--) {
                int newPercentage = mine.getContent().get(i).getPercentage() - percentage;
                mine.getContent().get(i).addPercentage(newPercentage);
            }
        } else {
            for(int i = mine.getContent().size() - 1; i > materialIndex; i--) {
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
    public void updateMinedPercentage(Mine mine) {
        mine.setMinedPercentage((mine.getArea() - 1) / mine.getArea());
        if(mine.getResetPercentage() <= mine.getMinedPercentage())
            mineResetRequest(mine);
    }

    private int getContainerIdByMaterial(Mine mine, Material material) {
        for(int i = 0; i < mine.getContent().size(); i++) {
            if(mine.getContent().get(i).getMaterial() == material) {
                return i;
            }
        }
        throw new IllegalStateException("Something went really wrong.");
    }

    public void setTeleportLocation(Mine mine, Location location) {
        mine.setTeleportLocation(location);
        mine.getFlags().add(Flags.TeleportLocation);
    }

    public void setResetPercentage(Mine mine, int percentage) {
        mine.setResetPercentage(percentage);
        if(!mine.getFlags().contains(Flags.PercentageReset)) {
            mine.getFlags().add(Flags.PercentageReset);
        }
    }

    public void mineResetRequest(Mine mine) {
        //lock.lock();
        resetQueue.add(mine);
        //lock.unlock();
        if(resetQueue.size() <= 1) {
            resetController();
        }
    }

    private void resetController() {
        new BukkitRunnable() {
            @Override
            public void run() {
                //lock.lock();
                for(Mine mine : resetQueue) {
                    mine.reset();
                }
                //lock.unlock();
            }
        }.runTaskTimer(AutoMines.getInstance(), 10, 20);
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
     * @param mine thats getting updated
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

    public boolean startMineResetTimer(Mine mine) {
        if(mine.getFlags().contains(Flags.TimeReset)) {
            return false;
        }
        mine.getFlags().add(Flags.TimeReset);
        mine.updateResetTime();
        return true;
    }

    public boolean stopMineResetTimer(Mine mine) {
        if(!mine.getFlags().contains(Flags.TimeReset)) {
            return false;
        }
        mine.getFlags().remove(Flags.TimeReset);
        return true;
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
