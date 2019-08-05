package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

class ResetTimer {

    private MineController mineController;

    ResetTimer(MineController mineController) {
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
            return false;
        }

        if (!mineController.validateMine(args[1])) {
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        return true;
    }
}
