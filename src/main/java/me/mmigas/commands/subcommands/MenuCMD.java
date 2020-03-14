package me.mmigas.commands.subcommands;

import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCMD extends CMD {
    public MenuCMD(MineController mineController) {
        super(mineController);
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidArgsLenght(args, 2)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_MENUCMD_USAGE);
            return;
        }
        if(invalidPlayer(commandSender) || invalidateMine(args[1], commandSender)) {
            return;
        }

        Player player = (Player) commandSender;

        Mine mine = mineController.getMine(args[1]);

        Menu menu = new Menu(mine, player);
        player.openInventory(menu.getCurrentInventory());
    }

    @Override
    public String getLabel() {
        return "menu";
    }
}
