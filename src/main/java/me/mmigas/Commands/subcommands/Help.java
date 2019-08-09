package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import org.bukkit.command.CommandSender;

public class Help {
    public boolean onCommand(CommandSender commandSender) {
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
