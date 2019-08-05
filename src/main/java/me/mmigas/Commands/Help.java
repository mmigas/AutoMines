package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import org.bukkit.command.CommandSender;

class Help {
    boolean onCommand(CommandSender commandSender) {
        LanguageManager.send(commandSender, "&bAutoMines commands:");
        LanguageManager.send(commandSender, LanguageManager.CREATE_USAGE);
        LanguageManager.send(commandSender, LanguageManager.DELETE_USAGE);
        LanguageManager.send(commandSender, LanguageManager.SET_AREA_USAGE);
        LanguageManager.send(commandSender, LanguageManager.CHANGE_BLOCK_USAGE);
        LanguageManager.send(commandSender, LanguageManager.RESET_USAGE);
        LanguageManager.send(commandSender, LanguageManager.RESET_TIMER_USAGE);
        return true;
    }
}
