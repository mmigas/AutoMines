package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

class Delete {

    private final MineController mineController;

    Delete(MineController mineController) {
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_DELETE_USAGE);
            return false;
        }

        if(!mineController.validateMine(args[1])){
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        mineController.deleteMine(mine);
        LanguageManager.send(commandSender, LanguageManager.MINE_DELETED);
        return true;
    }
}
