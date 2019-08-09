package me.mmigas.commands.subcommands;


import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.mmigas.files.LanguageManager;
import me.mmigas.math.BlockVector3D;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetArea {

    private final WorldEditPlugin worldEditPlugin;
    private final MineController mineController;

    public SetArea(WorldEditPlugin worldEditPlugin, MineController mineController) {
        this.worldEditPlugin = worldEditPlugin;
        this.mineController = mineController;
    }

    public boolean onCommand(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof Player)) {
            LanguageManager.send(commandSender, LanguageManager.MUST_BE_A_PLAYER);
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length != 2) {
            LanguageManager.send(commandSender, LanguageManager.WRONG_RESET_TIMER_USAGE);
        }

        if (!mineController.validateMine(args[1])) {
            LanguageManager.send(commandSender, LanguageManager.MINE_NOT_FOUND);
            return true;
        }

        Mine mine = mineController.getMine(args[1]);

        Region selection = null;
        try {
            selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        }

        if (selection != null && selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
            BlockVector3D minPosition = new BlockVector3D(selection.getMinimumPoint().getBlockX(), selection.getMinimumPoint().getBlockY(), selection.getMinimumPoint().getBlockZ());
            BlockVector3D maxPosition = new BlockVector3D(selection.getMaximumPoint().getBlockX(), selection.getMaximumPoint().getBlockY(), selection.getMaximumPoint().getBlockZ());
            mineController.setMineArea(mine, minPosition, maxPosition);
        } else {
            player.sendMessage("DEFINE THE FUCKING AREA FIRST");
        }

        player.sendMessage("changed");
        return true;
    }
}