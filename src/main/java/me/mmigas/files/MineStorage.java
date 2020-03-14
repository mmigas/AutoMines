package me.mmigas.files;

import me.mmigas.AutoMines;
import me.mmigas.math.BlockVector3D;
import me.mmigas.mines.Container;
import me.mmigas.mines.Flags;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MineStorage {
    private static final String NAME = "Mame";
    private static final String WORLD = "World";

    private static final String MINPOSITION = "MinPosition";
    private static final String MINPOSITION_X = "MinPosition.x";
    private static final String MINPOSITION_Y = "MinPosition.y";
    private static final String MINPOSITION_Z = "MinPosition.z";

    private static final String MAXPOSITION = "MaxPosition";
    private static final String MAXPOSITION_X = "MaxPosition.x";
    private static final String MAXPOSITION_Y = "MaxPosition.y";
    private static final String MAXPOSITION_Z = "MaxPosition.z";

    private static final String CONTENT = "Content";

    private static final String TELEPORT = "Teleport";
    private static final String TELEPORT_WORLD = "Teleport.world";
    private static final String TELEPORT_X = "Teleport.x";
    private static final String TELEPORT_Y = "Teleport.y";
    private static final String TELEPORT_Z = "Teleport.z";

    private static final String PERCENTAGE = "Percentage";
    private static final String TIMER = "Timer";

    private AutoMines plugin;

    private static final String EXTENSION = ".yml";

    private final File directory;

    public MineStorage(AutoMines plugin) {
        this.plugin = plugin;
        this.directory = new File(plugin.getDataFolder(), "Mines");
    }

    public void loadAllMines() {
        File[] files = directory.listFiles();

        if(files == null) {
            return;
        }

        FileConfiguration fileConfiguration = new YamlConfiguration();
        for(File file : files) {
            try {
                fileConfiguration.load(file);

                Mine mine;

                String name = fileConfiguration.getString(NAME);
                World mineWorld;
                BlockVector3D minPosition;
                BlockVector3D maxPosition;
                if(fileConfiguration.contains(WORLD) && fileConfiguration.contains(MINPOSITION) && fileConfiguration.contains(MAXPOSITION)) {
                    mineWorld = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString(WORLD)));

                    int minX = fileConfiguration.getInt(MINPOSITION_X);
                    int minY = fileConfiguration.getInt(MINPOSITION_Y);
                    int minZ = fileConfiguration.getInt(MINPOSITION_Z);

                    int maxX = fileConfiguration.getInt(MAXPOSITION_X);
                    int maxY = fileConfiguration.getInt(MAXPOSITION_Y);
                    int maxZ = fileConfiguration.getInt(MAXPOSITION_Z);

                    minPosition = new BlockVector3D(minX, minY, minZ);
                    maxPosition = new BlockVector3D(maxX, maxY, maxZ);

                    mine = new Mine(name, mineWorld, minPosition, maxPosition);
                } else {
                    mine = new Mine(name);
                }

                if(fileConfiguration.contains(CONTENT)) {
                    for(String string : Objects.requireNonNull(fileConfiguration.getConfigurationSection(CONTENT)).getKeys(false)) {
                        mine.getContent().add(new Container(Material.getMaterial(string), fileConfiguration.getInt(CONTENT + "." + string)));
                    }
                }

                if(fileConfiguration.contains(TELEPORT)) {
                    World tpWorld = Bukkit.getWorld(Objects.requireNonNull(fileConfiguration.getString(TELEPORT_WORLD)));
                    int x = fileConfiguration.getInt(TELEPORT_X);
                    int y = fileConfiguration.getInt(TELEPORT_Y);
                    int z = fileConfiguration.getInt(TELEPORT_Z);

                    plugin.getMineController().setTeleportLocation(mine, new Location(tpWorld, x, y, z));
                }

                if(fileConfiguration.contains(PERCENTAGE)){
                    int percentage = fileConfiguration.getInt(PERCENTAGE);
                    plugin.getMineController().setResetPercentage(mine, percentage);
                }

                plugin.getMineController().getMinesList().add(mine);
            } catch (IOException | InvalidConfigurationException e) {
                throw new IllegalStateException("Could not load file", e);
            }
        }

    }

    public void saveAllMines() {
        MineController mineController = plugin.getMineController();
        for(int i = 0; i < mineController.getMinesList().size(); i++) {
            saveMine(mineController.getMinesList().get(i));
        }
    }

    public void saveMine(Mine mine) {
        saveFile(createFileConfiguration(mine), mine.getName());
    }

    private void saveFile(FileConfiguration fileConfiguration, String name) {
        File file = new File(directory, name + EXTENSION);

        try {
            if(!file.exists()) {
                file.getParentFile().mkdirs();
            }

            fileConfiguration.save(file);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save file", e);
        }
    }

    private FileConfiguration createFileConfiguration(Mine mine) {
        FileConfiguration fileConfiguration = new YamlConfiguration();
        fileConfiguration.addDefault(NAME, mine.getName());
        if(mine.getMinPosition() != null && mine.getMaxPosition() != null) {
            fileConfiguration.addDefault(WORLD, mine.getWorld().getName());

            fileConfiguration.addDefault(MINPOSITION + ".x", mine.getMinPosition().getX());
            fileConfiguration.addDefault(MINPOSITION + ".y", mine.getMinPosition().getY());
            fileConfiguration.addDefault(MINPOSITION + ".z", mine.getMinPosition().getZ());
            fileConfiguration.addDefault(MAXPOSITION + ".x", mine.getMaxPosition().getX());
            fileConfiguration.addDefault(MAXPOSITION_Y, mine.getMaxPosition().getY());
            fileConfiguration.addDefault(MAXPOSITION_Z, mine.getMaxPosition().getZ());
        }

        if(!mine.getContent().isEmpty()) {
            for(Container container : mine.getContent()) {
                fileConfiguration.addDefault(CONTENT + "." + container.getMaterial(), container.getPercentage());
            }
        }

        if(mine.getFlags().contains(Flags.TeleportLocation)) {
            fileConfiguration.addDefault(TELEPORT_WORLD, mine.getTeleportLocation().getWorld().getName());
            fileConfiguration.addDefault(TELEPORT_X, mine.getTeleportLocation().getBlockX());
            fileConfiguration.addDefault(TELEPORT_Y, mine.getTeleportLocation().getBlockY());
            fileConfiguration.addDefault(TELEPORT_Z, mine.getTeleportLocation().getBlockZ());
        }

        if(mine.getFlags().contains(Flags.PercentageReset)) {
            fileConfiguration.addDefault(PERCENTAGE, mine.getResetPercentage());
        }

        if(mine.getFlags().contains(Flags.TimeReset)) {
            fileConfiguration.addDefault(TIMER, true);
        }

        fileConfiguration.options().copyDefaults(true);
        return fileConfiguration;
    }


}
