package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Help extends CMD {

    public Help(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        LanguageManager.sendMessage(commandSender, "&bAutoMines commands:");
        for(String string : LanguageManager.ALL_USAGES){
            LanguageManager.sendMessage(commandSender, string);
        }
    }

    @Override
    public String getLabel() {
        return "help";
    }
}
