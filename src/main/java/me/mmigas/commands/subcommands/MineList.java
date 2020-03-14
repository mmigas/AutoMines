package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;

public class MineList extends CMD {
    public MineList(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        for(Mine mine : mineController.getMinesList()){
            commandSender.sendMessage(mine.getName());
        }
    }

    @Override
    public String getLabel() {
        return "list";
    }
}
