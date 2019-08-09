package me.mmigas.commands.subcommands;


import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import me.mmigas.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AddBlock {

    private final MineController mineController;

    public AddBlock(MineController mineController) {
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length != 3) {
            LanguageManager.send(player, LanguageManager.WRONG_CHANGE_BLOCK_USAGE);
            return false;
        }

        if(!mineController.validateMine(args[1])){
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);
        Material blockHolding = Utils.getSolidBlockInPlayersHand(player);

        if (blockHolding == null)
            return true;

        int percentage = Integer.valueOf(args[2]);

        if (mineController.containsBlock(mine, blockHolding)) {
            changeBlock(player, mine, blockHolding, percentage);
        } else {
            addBlock(player, mine, blockHolding, percentage);
        }
        return true;
    }

    private void changeBlock(Player player, Mine mine, Material material, int percentage) {
        if (!mineController.changeBlock(mine, material, percentage)) {
            LanguageManager.send(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.send(player, LanguageManager.BLOCK_ADDED);
    }

    private void addBlock(Player player, Mine mine, Material material, int percentage) {
        if (!mineController.addBlock(mine, material, percentage)) {
            LanguageManager.send(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.send(player, LanguageManager.BLOCK_ADDED);
    }
}
