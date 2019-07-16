package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Mines.MineController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class Reset {

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (args.length != 1) {
			commandSender.sendMessage("Incorrect usage");
		}

		AutoMines.getInstance().getMineController().resetMine(args[0]);
		commandSender.sendMessage("IM FUCKING RESETING THIS BITCH");
		return true;
	}
}
