package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveGui extends CMD {
    public RemoveGui(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidPlayer(commandSender)) {
            return;
        }

        Player player = (Player) commandSender;

        if(invalidArgsLenght(args, 3)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_REMOVE_BLOCK_USAGE);
            return;
        }

        if(invalidateMine(args[1], commandSender)) {
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        Material material = Material.getMaterial(args[2]);

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
        return "removeGui";
    }
}
