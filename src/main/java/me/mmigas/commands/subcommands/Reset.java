package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Reset {

    private final MineController mineController;

    public  Reset(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_RESET_USAGE);
        }

        if (!mineController.validateMine(args[1])) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        mineController.mineResetRequest(mine);
        LanguageManager.sendKey(commandSender, LanguageManager.MINE_RESET);
        return true;
    }
}
