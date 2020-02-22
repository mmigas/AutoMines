package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import org.bukkit.command.CommandSender;

public class Help {
    public boolean onCommand(CommandSender commandSender) {
        LanguageManager.sendMessage(commandSender, "&bAutoMines commands:");
        LanguageManager.sendMessage(commandSender, LanguageManager.CREATE_USAGE);
        LanguageManager.sendMessage(commandSender, LanguageManager.DELETE_USAGE);
        LanguageManager.sendMessage(commandSender, LanguageManager.SET_AREA_USAGE);
        LanguageManager.sendMessage(commandSender, LanguageManager.CHANGE_BLOCK_USAGE);
        LanguageManager.sendMessage(commandSender, LanguageManager.RESET_USAGE);
        LanguageManager.sendMessage(commandSender, LanguageManager.RESET_TIMER_USAGE);
        return true;
    }
}
