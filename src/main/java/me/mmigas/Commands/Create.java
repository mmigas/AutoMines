package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import me.mmigas.mines.MineController;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class Create {

    private final WorldEditPlugin worldEditPlugin;
    private final MineController mineController;

    Create(WorldEditPlugin worldEditPlugin, MineController mineController) {
        this.worldEditPlugin = worldEditPlugin;
        this.mineController = mineController;
    }

    boolean onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_CREATE_USAGE);
            return true;
        }

        if (mineController.validateMine(args[1])) {
            LanguageManager.send(commandSender, LanguageManager.MINE_ALREADY_EXISTS);
            return true;
        }

        Region selection = null;
        try {
            selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        }

        if (selection != null && selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
            mineController.createMine(args[1], player.getWorld(), selection.getMinimumPoint(), selection.getMaximumPoint());
        } else {
            mineController.createMine(args[1]);
        }

        player.sendMessage("Created " + args[1]);

        return true;
    }


}