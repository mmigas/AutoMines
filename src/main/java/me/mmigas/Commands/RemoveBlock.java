package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import me.mmigas.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class RemoveBlock {

    private MineController mineController;

    RemoveBlock(MineController mineController) {
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_CHANGE_BLOCK_USAGE);
            return false;
        }

        if (!mineController.validateMine(args[1])) {
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        Material blockHolding = Utils.getSolidBlockInPlayersHand(player);
        if (blockHolding == null)
            return true;
        if (mineController.containsBlock(mine, blockHolding)) {
            mineController.removeBlock(mine, blockHolding);
            LanguageManager.send(player, LanguageManager.BLOCK_REMOVED);
        } else {
            LanguageManager.send(player, LanguageManager.MINE_DONT_HAVE_BLOCK);
        }

        return true;
    }
}
