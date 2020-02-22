package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import me.mmigas.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveBlock {

    private MineController mineController;

    public RemoveBlock(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return true;
        }

        Player player = (Player) commandSender;

        if(args.length != 2) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_CHANGE_BLOCK_USAGE);
            return false;
        }

        if(!mineController.validateMine(args[1])) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        Material blockHolding = Utils.getSolidBlockInPlayersHand(player);
        if(blockHolding == null)
            return true;
        if(mineController.containsBlock(mine, blockHolding)) {
            mineController.removeBlock(mine, blockHolding);
            LanguageManager.sendKey(player, LanguageManager.BLOCK_REMOVED, mine, blockHolding, mine.getMinedPercentage());
        } else {
            LanguageManager.sendKey(player, LanguageManager.MINE_DONT_HAVE_BLOCK, mine);
        }

        return true;
    }
}
