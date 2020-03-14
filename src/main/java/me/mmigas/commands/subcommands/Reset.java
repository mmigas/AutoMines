package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Reset extends CMD {
    public Reset(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 2)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_RESET_USAGE);
        }

        if(invalidateMine(args[1], commandSender)) {
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        mineController.mineResetRequest(mine);
        LanguageManager.sendKey(commandSender, LanguageManager.MINE_RESET);
    }

    @Override
    public String getLabel() {
        return "reset";
    }
}
