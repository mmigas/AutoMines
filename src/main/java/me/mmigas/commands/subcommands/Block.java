package me.mmigas.commands.subcommands;


import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.Material;
import org.bukkit.entity.Player;


public abstract class Block extends CMD {

    public Block(MineController mineController) {
        super(mineController);
    }

    void changeBlock(Player player, Mine mine, Material material, int percentage) {
        if(!mineController.changeBlock(mine, material, percentage)) {
            LanguageManager.sendKey(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.sendKey(player, LanguageManager.BLOCK_ADDED);
    }

    void addBlock(Player player, Mine mine, Material material, int percentage) {
        if(!mineController.addBlock(mine, material, percentage)) {
            LanguageManager.sendKey(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.sendKey(player, LanguageManager.BLOCK_ADDED, mine, material, percentage);
    }
}
