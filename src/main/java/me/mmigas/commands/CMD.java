package me.mmigas.commands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CMD {

    protected final MineController mineController;

    public CMD(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean invalidPlayer(CommandSender commandSender) {
        if(commandSender instanceof Player) {
            return false;
        }
        LanguageManager.sendMessage(commandSender, LanguageManager.MUST_BE_A_PLAYER);
        return true;
    }

    public boolean invalidArgsLenght(String[] args, int length) {
        return args.length != length;
    }

    public boolean invalidateMine(String mineName, CommandSender commandSender) {
        if(mineController.validateMine(mineName)) {
            return false;
        }

        LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
        return true;
    }

    public abstract void onCommand(CommandSender commandSender, String[] args);
    public abstract String getLabel();
}
