package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;

import org.bukkit.command.CommandSender;

class Reset {

    private final MineController mineController;

    Reset(MineController mineController) {
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_USAGE);
        }

        if (!mineController.validateMine(args[1])) {
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        mineController.resetMine(mine);
        LanguageManager.send(commandSender, LanguageManager.MINE_RESET);
        return true;
    }
}
