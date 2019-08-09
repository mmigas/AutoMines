package me.mmigas.mines;

import me.mmigas.AutoMines;
import me.mmigas.events.MineResetEvent;
import me.mmigas.math.BlockVector3D;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mine {
    private String name;
    private World world;
    private BlockVector3D minPosition, maxPosition;
    private Location teleportLocation;

    private List<Conteiner> conteiners = new ArrayList<>();
    private int total = 0;

    private ArrayList<Flags> flags = new ArrayList<>();

    private int length = 0;
    private int width = 0;
    private int height = 0;
    private int area = 0;

    private int totalPercentage;
    private int resetPercentage;


    public Mine(String name) {
        this.name = name;
        totalPercentage = 100;
    }

    public Mine(String name, World world, BlockVector3D minPosition, BlockVector3D maxPosition) {
        this.name = name;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.world = world;
        totalPercentage = 100;
        calculatedDimensions();
    }

    void reset() {
        ArrayList<Material> materialsGenerated = generatedMaterialsList();
        int counter = 0;
        for(int z = 0; z < length; z++) {
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    int finalX = minPosition.getX() + x;
                    int finalY = minPosition.getY() + y;
                    int finalZ = minPosition.getZ() + z;

                    world.getBlockAt(new Location(world, (double) finalX, (double) finalY, (double) finalZ)).setType(materialsGenerated.get(counter));
                    counter++;
                }
            }
        }
        MineResetEvent event = new MineResetEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);
        totalPercentage = 100;
    }

    private ArrayList<Material> generatedMaterialsList() {
        ArrayList<Material> materials = new ArrayList<>();

        Random random = new Random();
        nextBlock:
        for(int i = 0; i < area; i++) {
            int probabilityGenerated = random.nextInt(100);

            for(Conteiner conteiner : conteiners) {
                if(probabilityGenerated < conteiner.getPercentage()) {
                    materials.add(conteiner.getMaterial());
                    continue nextBlock;
                }
            }
            materials.add(Material.AIR);
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
        width = maxPosition.getX() - minPosition.getX() + 1;
        height = maxPosition.getY() - minPosition.getY() + 1;
        length = maxPosition.getZ() - minPosition.getZ() + 1;
        area = (int) (Math.sqrt(Math.pow(Math.sqrt(Math.pow(width, 2) * Math.pow(height, 2)), 2) * Math.pow(length, 2)));
    }

    public boolean containsBlock(Location location) {
        return minPosition != null && maxPosition != null && minPosition.getX() > location.getX() && maxPosition.getX() < location.getX() &&
                minPosition.getY() > location.getY() && maxPosition.getY() < location.getY() &&
                minPosition.getZ() > location.getZ() && maxPosition.getZ() < location.getZ();
    }

    public List<Player> playersInsideMine() {
        List<Player> players = new ArrayList<>();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(minPosition.getX() < player.getLocation().getX() && maxPosition.getX() > player.getLocation().getX() &&
                    minPosition.getY() < player.getLocation().getY() && maxPosition.getY() > player.getLocation().getY() &&
                    minPosition.getZ() < player.getLocation().getZ() && maxPosition.getZ() > player.getLocation().getZ()) {
                players.add(player);
            }
        }
        return players;
    }

    public void updateTotalPercentage() {
        totalPercentage = area - 1 / area;
        if(resetPercentage <= totalPercentage)
            reset();
    }

    public String getName() {
        return name;
    }

    public World getWorld() {
        return world;
    }

    public List<Conteiner> getContent() {
        return conteiners;
    }

    public List<Flags> getFlags() {
        return flags;
    }

    public Location getTeleportLocation() {
        return teleportLocation;
    }

    void setTeleportLocation(Location location) {
        teleportLocation = location;
    }

    void setMinPosition(BlockVector3D minPosition) {
        this.minPosition = minPosition;
    }

    void setMaxPosition(BlockVector3D maxPosition) {
        this.maxPosition = maxPosition;
    }

    public BlockVector3D getMinPosition() {
        return minPosition;
    }

    public BlockVector3D getMaxPosition() {
        return maxPosition;
    }

    int getWidth() {
        return width;
    }

    int getHeight() {
        return height;
    }

    public int getLength() {
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

    public void setResetPercentage(int percentage) {
        resetPercentage = percentage;
    }

    public Object getPercentage() {
        return resetPercentage;
    }

}

