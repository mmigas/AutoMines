package me.mmigas.Language;

import me.mmigas.AutoMines;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class LanguageFiles {

	private FileConfiguration languageConf;

	public LanguageFiles(){
		createLanguageFile();
	}

	private void createLanguageFile(){
		File languageFile = new File(AutoMines.getInstance().getDataFolder(), "pt_PT.yml");

		if(!languageFile.exists()){
			languageFile.getParentFile().mkdirs();
			AutoMines.getInstance().saveResource("pt_PT.yml", false);
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
