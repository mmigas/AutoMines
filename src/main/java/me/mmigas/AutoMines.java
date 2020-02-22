package me.mmigas;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.mmigas.commands.AutoMinesCommands;
import me.mmigas.files.LanguageManager;
import me.mmigas.listeners.BlockBreakListener;
import me.mmigas.listeners.InventoryListener;
import me.mmigas.listeners.MineCreateListener;
import me.mmigas.listeners.MineResetListener;
import me.mmigas.mines.MineController;
import me.mmigas.files.MineStorage;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class AutoMines extends JavaPlugin {
	private static AutoMines instance;

	private WorldEditPlugin worldEditPlugin;
	private MineController mineController;
	private MineStorage mineStorage;

	@Override
	public void onEnable(){
		instance = this;
		worldEditPlugin = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
        if(worldEditPlugin == null){
            getServer().getPluginManager().disablePlugin(this);
        }
		mineController = new MineController();
        new LanguageManager(this);
        mineStorage = new MineStorage(this);
		registerCommands();
        registerListener();

        mineStorage.loadAllMines();
	}

	@Override
	public void onDisable(){
        mineStorage.saveAllMines();
	}

    private void registerListener(){
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new MineResetListener(), this);
        getServer().getPluginManager().registerEvents(new MineCreateListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
    }

	private void registerCommands(){
		Objects.requireNonNull(this.getCommand("AutoMines")).setExecutor(new AutoMinesCommands());
	}

    public static AutoMines getInstance(){
        return instance;
    }

	public MineController getMineController(){
		return mineController;
	}

	public WorldEditPlugin getWorldEditPlugin(){
		return worldEditPlugin;
	}

	public MineStorage getMineStorage(){
	    return mineStorage;
    }

}
