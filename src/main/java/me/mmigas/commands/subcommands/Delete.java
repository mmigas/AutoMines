package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Delete extends CMD {

    private final MineController mineController;

    public Delete(MineController mineController) {
        super(mineController);
        this.mineController = mineController;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        if(!validateArgsLenght(args, 2) || !validateMine(args[1], commandSender)) {
            return true;
        }
        Mine mine = mineController.getMine(args[1]);

        mineController.deleteMine(mine);
        LanguageManager.sendKey(commandSender, LanguageManager.MINE_DELETED);
        return true;
    }

    @Override
    public String getLabel() {
        return "delete";
    }
}
