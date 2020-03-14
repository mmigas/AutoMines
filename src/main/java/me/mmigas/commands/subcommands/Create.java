package me.mmigas.commands.subcommands;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.math.BlockVector3D;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Create extends CMD {

    private final WorldEditPlugin worldEditPlugin;

    public Create(WorldEditPlugin worldEditPlugin, MineController mineController) {
        super(mineController);
        this.worldEditPlugin = worldEditPlugin;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidPlayer(commandSender)) {
            return;
        }

        Player player = (Player) commandSender;

        if(args.length != 2) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_CREATE_USAGE);
            return;
        }

        if(mineController.validateMine(args[1])) {
            LanguageManager.sendMessage(player, LanguageManager.MINE_ALREADY_EXISTS);
            return;
        }

        Region selection = null;
        try {
            selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        }

        if(selection != null && selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
            BlockVector3D minPosition = new BlockVector3D(selection.getMinimumPoint().getBlockX(), selection.getMinimumPoint().getBlockY(), selection.getMinimumPoint().getBlockZ());
            BlockVector3D maxPosition = new BlockVector3D(selection.getMaximumPoint().getBlockX(), selection.getMaximumPoint().getBlockY(), selection.getMaximumPoint().getBlockZ());
            mineController.createMine(args[1], player.getWorld(), minPosition, maxPosition);
        } else {
            mineController.createMine(args[1]);
        }

        player.sendMessage("Created " + args[1]);
    }

    @Override
    public String getLabel() {
        return "create";
    }


}