package me.mmigas.commands.subcommands;


import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import me.mmigas.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class AddBlock extends CMD {

    private final MineController mineController;

    private final String label = "AddBlock";

    public AddBlock(MineController mineController) {
        super(mineController);
        this.mineController = mineController;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, String[] args) {
        if(!validatePlayer(commandSender) || !validateArgsLenght(args, 3) || !validateMine(args[1], commandSender)) {
            return true;
        }

        Player player = (Player) commandSender;

        Mine mine = mineController.getMine(args[1]);
        Material blockHolding = Utils.getSolidBlockInPlayersHand(player);

        if(blockHolding == null){
            return true;
        }

        int percentage = Integer.parseInt(args[2]);

        if(mineController.containsBlock(mine, blockHolding)) {
            changeBlock(player, mine, blockHolding, percentage);
        } else {
            addBlock(player, mine, blockHolding, percentage);
        }
        return true;
    }

    private void changeBlock(Player player, Mine mine, Material material, int percentage) {
        if(!mineController.changeBlock(mine, material, percentage)) {
            LanguageManager.sendKey(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.sendKey(player, LanguageManager.BLOCK_ADDED);
    }

    private void addBlock(Player player, Mine mine, Material material, int percentage) {
        if(!mineController.addBlock(mine, material, percentage)) {
            LanguageManager.sendKey(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.sendKey(player, LanguageManager.BLOCK_ADDED);
    }

    @Override
    public String getLabel() {
        return "addblock";
    }

}
