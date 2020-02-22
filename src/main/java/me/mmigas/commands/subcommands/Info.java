package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Info extends CMD {


    private MineController mineController;

    public Info(MineController mineController) {
        super(mineController);
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if(!validateArgsLenght(args, 2)) {
            return true;
        }

        if(!validateMine(args[1], commandSender)){
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        String message = "&d=================================" + "&b" + args[1] + "%mine%'s info: \n" +
                "Reset Timer: " + mine.getResetCooldown() +
                "Reset Percentage: " + mine.getResetPercentage() +
                "\nDimensions: " +
                mine.getDimensions() +
                "\nBlock List: " +
                mine.getBlockList() +
                "&d=================================";
        LanguageManager.sendMessage(commandSender, message, mine);

        return true;
    }
}


