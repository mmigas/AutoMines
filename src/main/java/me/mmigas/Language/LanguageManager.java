package me.mmigas.Language;

import me.mmigas.AutoMines;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageManager {

	public static final String CREATE_USAGE = "mine-create-usage";
	public static final String DELETE_USAGE = "mine-delete-usage";
	public static final String CHANGE_BLOCK_USAGE = "change-block-usage";
	public static final String RESET_USAGE = "reset-usage";
	public static final String RESET_TIMER_USAGE = "reset-timer-usage";

	public static final String WRONG_RESET_USAGE = "wrong-reset-usage";
	public static final String WRONG_RESET_TIMER_USAGE = "wrong-reset-timer-usage";
	public static final String WRONG_CHANGE_BLOCK_USAGE = "wronge-change-block-usage";

	public static final String MINE_NOT_FOUND = "mine-not-found";
	public static final String EMPTY_HAND = "empty-hand";
	public static final String NOT_SOLID = "not-solid";

	private static final String FILE = "language.yml";

	public static final String MUST_BE_A_PLAYER = "You must be a player to executar that command!";

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
		fileConfiguration.addDefault(CREATE_USAGE, "&b/AutoMines create (mine)");
		fileConfiguration.addDefault(DELETE_USAGE, "&b/AutoMines delete (mine)");
		fileConfiguration.addDefault(CHANGE_BLOCK_USAGE, "&b/AutoMines block (mine) (percentage)");
		fileConfiguration.addDefault(RESET_USAGE, "&b/AutoMines reset (mine)");
		fileConfiguration.addDefault(RESET_TIMER_USAGE, "&b/AutoMines resetTimer (mine)");


		fileConfiguration.addDefault(WRONG_RESET_USAGE, "&cWrong command usage please use: /AutoMines block (mine) (percentage)");
		fileConfiguration.addDefault(WRONG_RESET_USAGE, "&cWrong command usage please use: /AutoMines create (mine)");
		fileConfiguration.addDefault(WRONG_RESET_TIMER_USAGE, "&cWrong command usage please use: /AutoMines timer (mine) (time)");

		fileConfiguration.addDefault(MINE_NOT_FOUND, "&cInvalid mine!");
		fileConfiguration.addDefault(EMPTY_HAND, "&cYour aren't holding anything!");
		fileConfiguration.addDefault(NOT_SOLID,"&cYour aren't holing a solid block!");
		fileConfiguration.options().copyDefaults(true);
		return fileConfiguration;
	}

	public static void send(CommandSender sender, String message){
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
}
