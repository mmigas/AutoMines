package me.mmigas.files;

import me.mmigas.AutoMines;
import me.mmigas.mines.Mine;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LanguageManager {


    public static final String CREATE_USAGE = "&b/AutoMines create (mine)";
    public static final String DELETE_USAGE = "&b/AutoMines delete (mine)";
    public static final String RESET_USAGE = "&b/AutoMines reset (mine)";
    public static final String SET_AREA_USAGE = "&b/AutoMines area (mine)";
    public static final String BLOCK_USAGE = "&b/AutoMines block (mine) (percentage)";
    public static final String REMOVE_USAGE = "&b/AutoMines remove (mine)";
    public static final String CHANGE_TIMER_USAGE = "&b/AutoMines changetimer (mine)";
    public static final String TELEPORT_USAGE = "&b/AutoMines tp (mine)";
    public static final String TIMER_USAGE = "&b/AutoMines timer (mine)";
    public static final String INFO_USAGE = "&b/AutoMines info (mine)";
    public static final String MENU_USAGE = "&b/AutoMines menu (mine)";

    public static final String[] ALL_USAGES = new String[]{CREATE_USAGE, DELETE_USAGE, RESET_USAGE, SET_AREA_USAGE, BLOCK_USAGE, REMOVE_USAGE, CHANGE_TIMER_USAGE, TELEPORT_USAGE, TIMER_USAGE, INFO_USAGE, MENU_USAGE};

    public static final String WRONG_CREATE_USAGE = "&cWrong command usage please use: /AutoMines create (mine)";
    public static final String WRONG_DELETE_USAGE = "&cWrong command usage please use: /AutoMines delete (mine)";
    public static final String WRONG_CHANGE_BLOCK_USAGE = "&cWrong command usage please use: /AutoMines block (mine) (percentage)";
    public static final String WRONG_REMOVE_BLOCK_USAGE = "&cWrong command usage please use: /AutoMines remove (mine)";
    public static final String WRONG_RESET_USAGE = "&cWrong command usage please use: /AutoMines reset (mine)";
    public static final String WRONG_RESET_TIMER_USAGE = "&cWrong command usage please use: /AutoMines timer (mine) (time)";
    public static final String WRONG_INFO_USAGE = "&cWrong command usage please use: /AutoMines info (mine)";
    public static final String WRONG_TELEPORT_USAGE = "&cWrong command usage please use: /AutoMine teleport (mine)";
    public static final String WRONG_MENUCMD_USAGE = "&cWrong command usage please use: /AutoMine menu (mine)";
    public static final String WRONG_SET_AREA_USAGE = "&cWrong command usage please use: /AutoMines area (mine)";
    ;

    public static final String MINE_NOT_FOUND = "mine-not-found";
    public static final String EMPTY_HAND = "empty-hand";
    public static final String NOT_BLOCK = "not-block";
    public static final String NOT_SOLID = "not-solid";
    public static final String MINE_RESET = "mine-reset";
    public static final String MINE_RESET_BROADCAST = "mine-reset-broadcast";
    public static final String MINE_ALREADY_EXISTS = "mine-already-exists";
    public static final String MINE_DELETED = "mine-deleted";
    public static final String BLOCK_ADDED = "block-added";
    public static final String BLOCK_REMOVED = "block-removed";
    public static final String MINE_DONT_HAVE_BLOCK = "mine-dont-have-block";
    public static final String PERCENTAGE_GREATER_THAN_100 = "percentage-grater-than-100";
    public static final String INVALID_RESET_TIMER = "invalid-reset-timer";
    public static final String RESET_TIMER_CHANGED = "reset-timer-changed";
    public static final String RESET_TIMER_START = "reset-timer-start";
    public static final String RESET_TIMER_STOP = "reset-timer-stop";

    public static final String MUST_BE_A_PLAYER = "&cYou must be a player to execute that command!";
    public static final String INVALID_COMMAND = "&cInvalid Command!";

    private static final String MINE_PLACEHOLDER = "%mine%";
    private static final String BLOCK_PLACEHOLDER = "%block%";
    private static final String MINE_TOTAL_PERCENTAGE_PLACEHOLDER = "%mined%";

    private static final String FILE = "language.yml";

    private AutoMines plugin;

    private Map<String, String> strings;

    private static LanguageManager instance;

    public LanguageManager(AutoMines plugin) {
        this.plugin = plugin;
        FileConfiguration fileConfiguration = createFileConfigurator();
        strings = new HashMap<>();
        instance = this;
        save(fileConfiguration);
        load(fileConfiguration);
    }

    private void save(FileConfiguration fileConfiguration) {
        File languageFile = new File(plugin.getDataFolder(), FILE);

        if (languageFile.exists()) {
            return;
        }

        languageFile.getParentFile().mkdirs();
        fileConfiguration.options().copyDefaults(true);
        try {
            fileConfiguration.save(languageFile);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save the language file!", e);
        }
    }

    private void load(FileConfiguration fileConfiguration) {
        File languageFile = new File(plugin.getDataFolder(), FILE);

        try {
            fileConfiguration.load(languageFile);

            Set<String> keys = fileConfiguration.getKeys(false);
            for (String key : keys) {
                String value = fileConfiguration.getString(key);
                strings.put(key, value);
            }
        } catch (IOException |
                InvalidConfigurationException e) {
            throw new IllegalStateException("Could not load the language file", e);
        }

    }

    private FileConfiguration createFileConfigurator() {
        FileConfiguration fileConfiguration = new YamlConfiguration();
        fileConfiguration.addDefault(MINE_NOT_FOUND, "&cInvalid mine!");
        fileConfiguration.addDefault(EMPTY_HAND, "&cYour aren't holding anything!");
        fileConfiguration.addDefault(NOT_BLOCK, "&cYour aren't holing a block!");
        fileConfiguration.addDefault(NOT_SOLID, "&cYour aren't holing a solid block!");
        fileConfiguration.addDefault(MINE_RESET, "&cMine resert successfully!");
        fileConfiguration.addDefault(MINE_RESET_BROADCAST, "&b" + MINE_PLACEHOLDER + " has reseted!");
        fileConfiguration.addDefault(MINE_ALREADY_EXISTS, "&cMine already exists!");
        fileConfiguration.addDefault(MINE_DELETED, "&cMine deleted!");
        fileConfiguration.addDefault(BLOCK_ADDED, "&b" + BLOCK_PLACEHOLDER + " added to " + MINE_PLACEHOLDER + " Total: " + MINE_TOTAL_PERCENTAGE_PLACEHOLDER + "!");
        fileConfiguration.addDefault(PERCENTAGE_GREATER_THAN_100, "&cPercentage can't be greater than 100. Total: " + MINE_TOTAL_PERCENTAGE_PLACEHOLDER + "!");
        fileConfiguration.addDefault(BLOCK_REMOVED, "&b" + BLOCK_PLACEHOLDER + " removed to " + MINE_PLACEHOLDER + ". Total: " + MINE_TOTAL_PERCENTAGE_PLACEHOLDER + "!");
        fileConfiguration.addDefault(MINE_DONT_HAVE_BLOCK, "&cThe " + MINE_PLACEHOLDER + " doesn't contians the " + BLOCK_PLACEHOLDER + "!");
        fileConfiguration.addDefault(INVALID_RESET_TIMER, "&c invalid reset cooldown!");
        fileConfiguration.options().copyDefaults(true);
        return fileConfiguration;
    }

    public static void sendKey(CommandSender sender, String key, Object... objects) {
        String message = instance.strings.get(key);
        sendMessage(sender, message, objects);
    }

    public static void sendMessage(CommandSender sender, String message, Object... objects) {
        message = replacePlaceHolders(message, objects);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private static String replacePlaceHolders(String message, Object... objects) {
        for (Object obj : objects) {
            if (obj instanceof Mine) {
                if (message.contains(MINE_PLACEHOLDER)) {
                    message = message.replace(MINE_PLACEHOLDER, ((Mine) obj).getName());
                }
                if (message.contains(MINE_TOTAL_PERCENTAGE_PLACEHOLDER)) {
                    message = message.replace(MINE_TOTAL_PERCENTAGE_PLACEHOLDER, String.valueOf(obj));
                }

            } else if (obj instanceof ItemStack && message.contains(BLOCK_PLACEHOLDER)) {
                message = message.replace(BLOCK_PLACEHOLDER, obj.toString());
            }
        }
        return message;
    }
}
