package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Flags;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class Timer extends CMD {
    public Timer(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(args.length != 2) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
            return;
        }

        if(!mineController.validateMine(args[1])) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        if(mine.getFlags().contains(Flags.TimeReset)) {
            mineController.startMineResetTimer(mine);
            LanguageManager.sendKey(commandSender, LanguageManager.RESET_TIMER_START, mine);
        } else {
            mineController.stopMineResetTimer(mine);
            LanguageManager.sendKey(commandSender, LanguageManager.RESET_TIMER_STOP, mine);
        }
    }

    @Override
    public String getLabel() {
        return "timer";
    }
}
