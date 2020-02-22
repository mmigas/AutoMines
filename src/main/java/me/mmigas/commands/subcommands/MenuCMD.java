package me.mmigas.commands.subcommands;

import me.mmigas.files.LanguageManager;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuCMD {

    private final MineController mineController;

    public MenuCMD(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if(!(commandSender instanceof Player)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return false;
        }

        Player player = (Player) commandSender;

        if(!mineController.validateMine(args[1])){
            LanguageManager.sendKey(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        Menu menu = new Menu(mine, player);
        player.openInventory(menu.getCurrentInventory());
        return true;
    }
}
