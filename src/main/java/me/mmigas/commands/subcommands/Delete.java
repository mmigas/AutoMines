package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Delete extends CMD {
    public Delete(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 2) || invalidateMine(args[1], commandSender)) {
            return;
        }
        Mine mine = mineController.getMine(args[1]);

        mineController.deleteMine(mine);
        LanguageManager.sendKey(commandSender, LanguageManager.MINE_DELETED);
    }

    @Override
    public String getLabel() {
        return "delete";
    }
}
