package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import me.mmigas.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemovePlayer extends CMD {

    private MineController mineController;

    public RemovePlayer(MineController mineController) {
        super(mineController);
        this.mineController = mineController;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidPlayer(commandSender)) {
            return;
        }

        Player player = (Player) commandSender;

        if(invalidArgsLenght(args, 2)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_REMOVE_BLOCK_USAGE);
            return;
        }

        if(invalidateMine(args[1], commandSender)) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        Material material = Utils.getSolidBlockInPlayersHand(player);

        if(material == null)
            return;
        if(mineController.containsMateiral(mine, material)) {
            mineController.removeBlock(mine, material);
            LanguageManager.sendKey(player, LanguageManager.BLOCK_REMOVED, mine, material);
        } else {
            LanguageManager.sendKey(player, LanguageManager.MINE_DONT_HAVE_BLOCK, mine, material);
        }
    }

    @Override
    public String getLabel() {
        return "remove";
    }
}
