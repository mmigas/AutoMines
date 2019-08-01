package me.mmigas.Commands;

import me.mmigas.Language.LanguageManager;
import org.bukkit.command.CommandSender;


class ResetTimer {

	private MineController mineController;

	ResetTimer(MineController mineController) {
		this.mineController = mineController;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (args.length == 0) {
			LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
			return false;
		}

		if (!mineController.validatMine(args[1])) {
			commandSender.sendMessage("No Mine found");
			return false;
		}

		return true;
	}
}
