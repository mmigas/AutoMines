package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Mines.MineController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

 class Delete{

	 boolean onCommand(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage("OLHA DEU MERDA");
			return false;
		}

		Player player = (Player) commandSender;

		if (args.length != 1)
			player.sendMessage("Incorrect usage");

		AutoMines.getInstance().getMineController().deleteMine(args[0]);
		player.sendMessage("Deleted");
		return true;
	}
}
