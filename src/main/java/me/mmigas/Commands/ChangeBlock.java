package me.mmigas.Commands;


import com.sun.org.apache.bcel.internal.generic.LNEG;
import me.mmigas.AutoMines;
import me.mmigas.Language.LanguageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


class ChangeBlock {

	private final MineController mineController;

	ChangeBlock(MineController mineController){
		this.mineController = mineController;
	}

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player)) {
			LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
			return false;
		}

		Player player = (Player) commandSender;

		if (args.length != 2) {
			LanguageManager.send(player, LanguageManager.WRONG_CHANGE_BLOCK_USAGE);
			return false;
		}

		if (!AutoMines.getInstance().getMineController().validatMine(args[0])) {
			LanguageManager.send(player,LanguageManager.MINE_NOT_FOUND);
			return false;
		}

		ItemStack itemHolding = player.getInventory().getItemInMainHand();
		if (!itemHolding.hasItemMeta()) {
			LanguageManager.send(player, LanguageManager.EMPTY_HAND);
			return false;
		}
		if (!itemHolding.getType().isBlock()) {
			player.sendMessage("Please hold a solid block");
			return false;
		}
		if (!itemHolding.getType().isSolid()) {
			LanguageManager.send(player, LanguageManager.NOT_SOLID);
			return false;
		}

		double percentage = Double.valueOf(args[2]);
		mineController.changeBlock(args[2], itemHolding.getType(), percentage);
		return true;
	}
}
