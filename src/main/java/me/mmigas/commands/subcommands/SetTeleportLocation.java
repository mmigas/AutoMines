package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeleportLocation extends CMD {
    public SetTeleportLocation(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidPlayer(commandSender)) {
            return;
        }

        if(invalidArgsLenght(args, 2)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_TELEPORT_USAGE);
            return;
        }

        Player player = (Player) commandSender;

        if(invalidateMine(args[1], commandSender)) {
            return;
        }

        Mine mine = mineController.getMine(args[1]);
        mineController.setTeleportLocation(mine, player.getLocation());

    }

    @Override
    public String getLabel() {
        return "tp";
    }

}

