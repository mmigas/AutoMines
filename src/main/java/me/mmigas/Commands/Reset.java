package me.mmigas.Commands;

import me.mmigas.AutoMines;
import me.mmigas.Language.LanguageManager;
import me.mmigas.Mines.MineController;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

class Reset {

	private final MineController mineController;

	Reset(MineController mineController){
		this.mineController = mineController;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (args.length != 1) {
			LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_COMMAND_USAGE);
		}

		if(!mineController.validatMine(args[1])){
			commandSender.sendMessage("No mine found");
			return false;
		}

		mineController.resetMine(args[1]);
		commandSender.sendMessage("IM FUCKING RESETING THIS BITCH");
		return true;
	}
}
