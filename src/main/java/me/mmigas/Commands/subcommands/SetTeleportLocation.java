package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTeleportLocation {

    private MineController mineController;

    public SetTeleportLocation(MineController mineController) {
        this.mineController = mineController;
    }

   public boolean onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return true;
        }

        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_TELEPORT_USAGE);
            return true;
        }

        Player player = (Player) commandSender;

        if (!mineController.validateMine(args[1])) {
            LanguageManager.send(player, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);
        mineController.setTeleportLocation(mine, player.getLocation());
        return true;
    }

}

