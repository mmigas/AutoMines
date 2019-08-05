package me.mmigas.language;

import me.mmigas.AutoMines;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageManager {

	public static final String CREATE_USAGE = "&b/AutoMines create (mine)";
	public static final String DELETE_USAGE = "&b/AutoMines delete (mine)";
	public static final String SET_AREA_USAGE = "&b/AutoMines setArea (mine)";
	public static final String CHANGE_BLOCK_USAGE = "&b/AutoMines block (mine) (percentage)";
	public static final String RESET_USAGE = "&b/AutoMines reset (mine)";
	public static final String RESET_TIMER_USAGE = "&b/AutoMines resetTimer (mine)";

	public static final String WRONG_CREATE_USAGE = "&cWrong command usage please use: /AutoMines create (mine)";
	public static final String WRONG_DELETE_USAGE = "&cWrong command usage please use: /AutoMines delete (mine)";
    public static final String WRONG_CHANGE_BLOCK_USAGE = "&cWrong command usage please use: /AutoMines block (mine) (percentage)";
    public static final String WRONG_RESET_USAGE = "&cWrong command usage please use: /AutoMines reset (mine)";
    public static final String WRONG_RESET_TIMER_USAGE = "&cWrong command usage please use: /AutoMines timer (mine) (time)";
    public static final String WRONG_INFO_USAGE = "&cWrong usage please use: /AutoMines info (mine)";

    public static final String MINE_NOT_FOUND = "mine-not-found";
    public static final String EMPTY_HAND = "empty-hand";
    public static final String NOT_BLOCK = "not-block";
    public static final String NOT_SOLID = "not-solid";
    public static final String MINE_RESET = "mine-reset";
    public static final String MINE_ALREADY_EXISTS = "mine-already-exists";
    public static final String MINE_DELETED = "mine-deleted";
    public static final String BLOCK_ADDED = "block-added";
    public static final String BLOCK_REMOVED = "block-removed";
    public static final String MINE_DONT_HAVE_BLOCK = "mine-dont-have-block";
    public static final String PERCENTAGE_GREATER_THAN_100 = "percentage-grater-than-100";

    public static final String MUST_BE_A_PLAYER = "&cYou must be a player to executar that command!";
    public static final String INVALID_COMMAND = "&cInvalid Command!";

    private static final String FILE = "language.yml";

	private AutoMines plugin;

    public LanguageManager(AutoMines plugin) {
        this.plugin = plugin;
        FileConfiguration fileConfiguration = createFileConfigurator();
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
		} catch (IOException | InvalidConfigurationException e ) {
			throw new IllegalStateException("Could not load the language file", e);
		}
	}

	private FileConfiguration createFileConfigurator() {
		FileConfiguration fileConfiguration = new YamlConfiguration();
		fileConfiguration.addDefault(MINE_NOT_FOUND, "&cInvalid mine!");
		fileConfiguration.addDefault(EMPTY_HAND, "&cYour aren't holding anything!");
		fileConfiguration.addDefault(NOT_BLOCK,"&cYour aren't holing a block!");
		fileConfiguration.addDefault(NOT_SOLID,"&cYour aren't holing a solid block!");
		fileConfiguration.addDefault(MINE_RESET,"&cMine resert successfully!");
		fileConfiguration.addDefault(MINE_ALREADY_EXISTS,"&cMine already exists!");
		fileConfiguration.addDefault(MINE_DELETED,"&cMine deleted!");
		fileConfiguration.addDefault(BLOCK_ADDED,"&b%item% added to %mine%.\nTotal: %total%!");
		fileConfiguration.addDefault(PERCENTAGE_GREATER_THAN_100,"&cPercentage can't be greater than 100.\n Total: %total%");
		fileConfiguration.addDefault(BLOCK_REMOVED,"&b%item% removed to %mine%.\nTotal: %total%!");
		fileConfiguration.addDefault(MINE_DONT_HAVE_BLOCK,"&cThe %mine% doesn't contians the %block%!");
		fileConfiguration.options().copyDefaults(true);
		return fileConfiguration;
	}

	public static void send(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}
