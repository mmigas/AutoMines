package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class ChangeResetTimer extends CMD {
    public ChangeResetTimer(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 3)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
        }

        if(invalidateMine(args[1], commandSender)) {
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        if(!mineController.setResetTimer(mine, Long.parseLong(args[2]))) {
            LanguageManager.sendKey(commandSender, LanguageManager.INVALID_RESET_TIMER);
            return;
        }
        LanguageManager.sendMessage(commandSender, LanguageManager.RESET_TIMER_CHANGED);
    }

    @Override
    public String getLabel() {
        return "changetimer";
    }
}
