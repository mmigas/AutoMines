package me.mmigas.Language;

import me.mmigas.AutoMines;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static me.mmigas.AutoMines.*;

public class LanguageFiles {

	private FileConfiguration languageConf;

	public LanguageFiles(){
		createLanguageFile();
	}

	private void createLanguageFile(){
		File languageFile = new File(AutoMines.getInstance().getDataFolder(), "en_ENG.yml");

		if(!languageFile.exists()){
			languageFile.getParentFile().mkdirs();
			getInstance().saveResource("en_ENG.yml", false);
		}

		languageConf = new YamlConfiguration();

		try {
			languageConf.load(languageFile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}

	public FileConfiguration getLanguageConf(){
		return  languageConf;
	}

}
