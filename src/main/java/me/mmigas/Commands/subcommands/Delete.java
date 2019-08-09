package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Delete {

    private final MineController mineController;

    public Delete(MineController mineController) {
        this.mineController = mineController;
    }

    public  boolean onCommand(CommandSender commandSender, String[] args) {
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
