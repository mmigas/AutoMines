package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Mines.MineController;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

 class Create {

	private final WorldEditPlugin worldEditPlugin;
	private final MineController mineController;

	 Create(WorldEditPlugin worldEditPlugin, MineController mineController) {
		this.worldEditPlugin = worldEditPlugin;
		this.mineController = mineController;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage(AutoMines.getInstance().getLanguageFiles().getLanguageConf().getString("PlayerCommand"));
			return false;
		}

		Player player = (Player) commandSender;

		if (args.length != 2) {
			player.sendMessage("Incorrect usage");
			return false;
		}

		if(mineController.validatMine(args[1])){
			player.sendMessage("Mine already exist");
			return false;
		}

		Region selection = null;
		try {
			selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
		} catch (IncompleteRegionException e) {
			e.printStackTrace();
		}

		if (selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
			mineController.createMine(args[1], player.getWorld(), selection.getMinimumPoint(), selection.getMaximumPoint());
		} else {
			mineController.createMine(args[1]);
		}

		player.sendMessage("Created " + args[1]);

		return true;
	}


}