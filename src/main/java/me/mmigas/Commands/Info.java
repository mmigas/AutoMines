package me.mmigas.Commands;

import com.sun.org.apache.bcel.internal.generic.LNEG;
import me.mmigas.Language.LanguageManager;
import org.bukkit.command.CommandSender;

public class Info{

	boolean onCommand(CommandSender sender){
		LanguageManager.send(sender, "AutoMines commands:");
		LanguageManager.send(sender, LanguageManager.CREATE_USAGE);
		LanguageManager.send(sender, LanguageManager.DELETE_USAGE);
		LanguageManager.send(sender, LanguageManager.CHANGE_BLOCK_USAGE);
		LanguageManager.send(sender, LanguageManager.RESET_USAGE);
		LanguageManager.send(sender, LanguageManager.RESET_TIMER_USAGE);


		return true;
	}
}
