package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BlockGui extends Block {

    public BlockGui(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 4)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_CHANGE_BLOCK_USAGE);
            return;
        }

        if(invalidPlayer(commandSender) || invalidateMine(args[1], commandSender)) {
            return;
        }

        Player player = (Player) commandSender;
        Mine mine = mineController.getMine(args[1]);
        Material material = Material.getMaterial(args[2]);

        if(material == null) {
            return;
        }

        int percentage = Integer.parseInt(args[3]);

        if(mineController.containsMateiral(mine, material)) {
            changeBlock(player, mine, material, percentage);
        } else {
            addBlock(player, mine, material, percentage);
        }
    }

    @Override
    public String getLabel() {
        return "blockgui";
    }
}
