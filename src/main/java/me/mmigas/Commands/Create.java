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

	 Create(WorldEditPlugin worldEditPlugin) {
		this.worldEditPlugin = worldEditPlugin;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage("OLHA DEU MERDA");
			return false;
		}

		Player player = (Player) commandSender;

		if (args.length != 1) {
			player.sendMessage("Incorrect usage");
		}

		Region selection = null;
		try {
			selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
		} catch (IncompleteRegionException e) {
			e.printStackTrace();
		}

		if (selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
			Location minLocation = new Location(player.getWorld(), selection.getMinimumPoint().getBlockX(), selection.getMinimumPoint().getBlockY(), selection.getMinimumPoint().getBlockZ());
			Location maxLocation = new Location(player.getWorld(), selection.getMaximumPoint().getBlockX(), selection.getMaximumPoint().getBlockY(), selection.getMaximumPoint().getBlockZ());
			AutoMines.getInstance().getMineController().createMine(args[0], player.getWorld(), minLocation, maxLocation);
		} else {
			AutoMines.getInstance().getMineController().createMine(args[0]);
		}

		player.sendMessage("Created");

		return true;
	}


}