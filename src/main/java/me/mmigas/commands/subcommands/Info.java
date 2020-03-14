package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Info extends CMD {

    public Info(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 2)) {
            return;
        }

        if(invalidateMine(args[1], commandSender)) {
            return;
        }

        Mine mine = mineController.getMine(args[1]);
        String message = "&d=================================" + "&b %mine%'s info: \n" +
                "Reset Timer: " + mine.getResetCooldown() +
                "\nReset Percentage: " + mine.getResetPercentage() +
                "\nDimensions: " +
                mine.getDimensions() +
                "\nBlock List: " +
                mine.getBlockList() +
                "&d=================================";
        LanguageManager.sendMessage(commandSender, message, mine);
    }

    @Override
    public String getLabel() {
        return "info";
    }
}


