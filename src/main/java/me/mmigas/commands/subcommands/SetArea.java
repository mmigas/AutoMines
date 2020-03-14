package me.mmigas.commands.subcommands;


import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;
import me.mmigas.commands.CMD;
import me.mmigas.files.LanguageManager;
import me.mmigas.math.BlockVector3D;
import me.mmigas.mines.Mine;
import me.mmigas.mines.MineController;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetArea extends CMD {

    private final WorldEditPlugin worldEditPlugin;

    public SetArea(WorldEditPlugin worldEditPlugin, MineController mineController) {
        super(mineController);
        this.worldEditPlugin = worldEditPlugin;
    }

    @Override
    public void onCommand(CommandSender commandSender, String[] args) {
        if(invalidPlayer(commandSender)) {
            return;
        }

        Player player = (Player) commandSender;

        if(invalidArgsLenght(args, 2)) {
            LanguageManager.sendMessage(commandSender, LanguageManager.WRONG_SET_AREA_USAGE);
            return;
        }

        if(invalidateMine(args[1], commandSender)) {
            return;
        }

        Mine mine = mineController.getMine(args[1]);

        Region selection = null;
        try {
            selection = worldEditPlugin.getSession(player).getSelection(BukkitAdapter.adapt(player.getWorld()));
        } catch (IncompleteRegionException e) {
            e.printStackTrace();
        }

        if(selection != null && selection.getMinimumPoint() != null && selection.getMaximumPoint() != null) {
            BlockVector3D minPosition = new BlockVector3D(selection.getMinimumPoint().getBlockX(), selection.getMinimumPoint().getBlockY(), selection.getMinimumPoint().getBlockZ());
            BlockVector3D maxPosition = new BlockVector3D(selection.getMaximumPoint().getBlockX(), selection.getMaximumPoint().getBlockY(), selection.getMaximumPoint().getBlockZ());
            mineController.setMineArea(mine, minPosition, maxPosition);
        } else {
            player.sendMessage("DEFINE THE FUCKING AREA FIRST");
        }

        player.sendMessage("changed");
    }

    @Override
    public String getLabel() {
        return "area";
    }
}