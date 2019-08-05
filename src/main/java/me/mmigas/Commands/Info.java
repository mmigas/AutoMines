package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

class Info {


    private MineController mineController;

    Info(MineController mineController) {
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_INFO_USAGE);
            return true;
        }

        if(!mineController.validateMine(args[1])){
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }
        Mine mine = mineController.getMine(args[1]);

        StringBuilder builder = new StringBuilder();
        builder.append("&b").append(args[1]).append("'s info: \n")
                .append("Dimensions: \n")
                .append(mineController.getDimensions(mine))
                .append("Block List: \n")
                .append(mineController.getBlockList(mine));
        LanguageManager.send(commandSender, builder.toString());

        return true;
    }
}


