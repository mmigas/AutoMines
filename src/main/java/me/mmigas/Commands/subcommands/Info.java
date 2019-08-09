package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Info {


    private MineController mineController;

    public Info(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
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
        commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',builder.toString()));

        return true;
    }
}


