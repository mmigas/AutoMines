package me.mmigas;

import me.mmigas.Commands.*;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.mmigas.Language.LanguageManager;
import me.mmigas.Mines.MineController;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoMines extends JavaPlugin {
	private static AutoMines instance;

	private WorldEditPlugin worldEditPlugin;
	private MineController mineController;

	private LanguageManager languageFiles;

	@Override
	public void onEnable(){
		//load into memory all the mines
		instance = this;
		worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		mineController = new MineController();
		languageFiles = new LanguageManager(this);
		registerCommands();
	}

	@Override
	public void onDisable(){


		//Load into files all the mines
	}


	private void registerCommands(){
		this.getCommand("AutoMinesCommand").setExecutor(new AutoMinesCommands());
	}

	public MineController getMineController(){
		return mineController;
	}

	public static AutoMines getInstance(){
		return instance;
	}

	public WorldEditPlugin getWorldEditPlugin(){
		return worldEditPlugin;
	}

	public LanguageManager getLanguageFiles(){
		return languageFiles;
	}

}
