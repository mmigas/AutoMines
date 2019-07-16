package me.mmigas.Commands;


import me.mmigas.AutoMines;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


class ChangeBlock {

	boolean onCommand(CommandSender commandSender, String[] args) {
		if (!(commandSender instanceof Player)) {
			commandSender.sendMessage("Olha deu merda");
			return false;
		}

		Player player = (Player) commandSender;

		if (args.length != 2) {
			player.sendMessage("Invalid usage");
			return false;
		}

		if (!AutoMines.getInstance().getMineController().validatMine(args[0])) {
			player.sendMessage("No mine found");
		}

		ItemStack itemHolding = player.getItemOnCursor();
		if (!itemHolding.hasItemMeta()) {
			player.sendMessage("Your not holding anything");
			return false;
		}
		if (!itemHolding.getType().isBlock()) {
			player.sendMessage("Please hold a block");
			return false;
		}
		if (!itemHolding.getType().isSolid()) {
			player.sendMessage("Please hold a solid block");
			return false;
		}

		double percentage = Double.valueOf(args[0]);
		AutoMines.getInstance().getMineController().changeBlock(args[0], itemHolding.getType(), percentage);
		return true;
	}
}
