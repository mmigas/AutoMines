package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Mines.MineController;
import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


class SetArea {

	private final WorldEditPlugin worldEditPlugin;
	private final MineController mineController;

	SetArea(WorldEditPlugin worldEditPlugin, MineController mineController) {
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
		}

		Region selection = null;
		try {
			selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
		} catch (IncompleteRegionException e) {
			e.printStackTrace();
		}

		if (selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
			mineController.setMineArea(args[1], selection.getMinimumPoint(), selection.getMaximumPoint());
		} else {
			player.sendMessage("DEFINE THE FUCKING AREA FIRST");
		}

		player.sendMessage("changed");
		return true;
	}
}