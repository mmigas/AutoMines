package me.mmigas.Commands;

import me.mmigas.Language.LanguageManager;
import org.bukkit.command.CommandSender;

class ResetTimer {


	boolean onCommand(CommandSender commandSender, String[] args) {
		if (args.length == 0) {
			LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
			return false;
		}


		return true;
	}
}
