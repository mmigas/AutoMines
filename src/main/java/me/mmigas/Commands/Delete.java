package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Mines.MineController;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class Delete {

	private final MineController mineController;

	Delete(MineController mineController) {
		this.mineController = mineController;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (args.length != 2) {
			commandSender.sendMessage("Incorrect usage");
			return false;
		}

		if (mineController.validatMine(args[1])) {
			commandSender.sendMessage("No Mine found");
			return false;
		}

		mineController.deleteMine(args[1]);
		commandSender.sendMessage("Deleted");
		return true;
	}
}
