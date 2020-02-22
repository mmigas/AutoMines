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

    private List<Flags> flags = new ArrayList<>();

    private int length = 0;
    private int width = 0;
    private int height = 0;
    private int area = 0;

    private int minedPercentage;
    private int resetPercentage;
    private long resetCooldown;
    private int resetEffectCooldown;
    private boolean isReseting = false;
    private long resetTime;

    Random random = new Random();

    public Mine(String name) {
        this.name = name;
        minedPercentage = 0;
    }

    public Mine(String name, World world, BlockVector3D minPosition, BlockVector3D maxPosition) {
        this.name = name;
        this.minPosition = minPosition;
        this.maxPosition = maxPosition;
        this.world = world;
        minedPercentage = 0;
        calculatedDimensions();
    }

    /**
     * Processes the reset request by the minecontroller
     */
    void reset() {
        isReseting = true;
        List<Material> materialsGenerated = generateMaterialsList();

        if(resetEffectCooldown == 0) {
            normalReset(materialsGenerated);
        } else {
            resetWithEffect(materialsGenerated);
        }

        MineResetEvent event = new MineResetEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(event);
        minedPercentage = 0;
        isReseting = false;
    }

    /**
     * Normal reset without any effect
     * @param materials
     */
    private void normalReset(List<Material> materials) {
        for(int z = 0; z < length; z++) {
            for(int y = 0; y < height; y++) {
                for(int x = 0; x < width; x++) {
                    world.getBlockAt(getMinPosition().getX() + x, getMinPosition().getY() + y, getMinPosition().getZ() + z).setType(materials.iterator().next());
                }
            }
        }
    }

    /**
     * Resets mine with the per block cooldown effect
     * @param materials
     */
    private void resetWithEffect(List<Material> materials) {
        new BukkitRunnable() {
            int x, y, z;

            @Override
            public void run() {
                if(x >= width) {
                    z++;
                    x = 0;
                }
                if(z >= length) {
                    y++;
                    z = 0;
                }
                if(y >= height) {
                    this.cancel();
                } else {
                    world.getBlockAt(getMinPosition().getX() + x, getMinPosition().getY() + y, getMinPosition().getZ() + z).setType(materials.iterator().next());
                    x++;
                }

            }

        }.runTaskTimer(AutoMines.getInstance(), 0, resetEffectCooldown);
    }

    public void updateResetTime(){
        setResetTime(System.currentTimeMillis() + resetCooldown);
    }

    /**
     * Genererates the materials list for the new reset
     * @return ArrayList with all the blocks
     */
    private List<Material> generateMaterialsList() {
        List<Material> materials = new ArrayList<>();

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

    /**
     * Calculates the dimensions of the new Area
     */
    void calculatedDimensions() {
        width = maxPosition.getX() - minPosition.getX() + 1;
        height = maxPosition.getY() - minPosition.getY() + 1;
        length = maxPosition.getZ() - minPosition.getZ() + 1;
        area = width * height * length;
    }

    /**
     * checks if the location is insidade this mine
     *
     * @param location to be checkd
     * @return true if is inside the mine false otherwise
     */
    public boolean containsBlock(Location location) {
        return minPosition != null && maxPosition != null && minPosition.getX() > location.getX() && maxPosition.getX() < location.getX() &&
                minPosition.getY() > location.getY() && maxPosition.getY() < location.getY() &&
                minPosition.getZ() > location.getZ() && maxPosition.getZ() < location.getZ();
    }

    /**
     * Gets all the players currently inside the mine
     * @return ArrayList with all the players
     */
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


    /**
     * For info command
     *
     * @return String with information
     */
    public String getDimensions() {
        return "MinPosition: " + getMinPosition() + "\n" +
                "MaxPosition: " + getMaxPosition() + "\n" +
                "Width: " + getWidth() + "\n" +
                "Height: " + getHeight() + "\n" +
                "Length: " + getLength() + "\n" +
                "Area: " + getArea() + "\n";
    }

    /**
     * For info command
     *
     * @return String with information
     */
    public String getBlockList() {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < getContent().size(); i++) {
            builder.append("- ").append(getContent().get(i).getMaterial()).append(" ").append(getContent().get(i).getPercentage()).append("\n");
        }
        return builder.toString();
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

    public long getResetCooldown() {
        return resetCooldown;
    }

    public void setResetCooldown(long cooldown) {
        resetCooldown = cooldown;
    }

    public int getResetPercentage() {
        return resetPercentage;
    }

    public void setMinedPercentage(int minedPercentage) {
        this.minedPercentage = minedPercentage;
    }

    public int getMinedPercentage() {
        return minedPercentage;
    }

    public void setResetEffectCooldown(int cooldown) {
        this.resetEffectCooldown = cooldown;
    }

    public int getResetEffectCooldown() {
        return resetEffectCooldown;
    }

    public boolean isReseting() {
        return isReseting;
    }

    public long getResetTime(){
        return resetTime;
    }

    public void setResetTime(long resetTime){
        this.resetTime = resetTime;
    }
}

