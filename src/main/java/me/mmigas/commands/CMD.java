package me.mmigas.commands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CMD {

    private final MineController mineController;

    public CMD(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean validatePlayer(CommandSender commandSender){
        if(commandSender instanceof Player){
            LanguageManager.sendMessage(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return true;
        }
        return false;
    }

    public boolean validateArgsLenght(String[] args, int length) {
        return args.length == length;
    }

    public boolean validateMine(String mineName, CommandSender commandSender) {
        if(!mineController.validateMine(mineName)) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return false;
        }

        return true;
    }

    public abstract boolean onCommand(CommandSender commandSender, String[] args);
    public abstract String getLabel();
}
