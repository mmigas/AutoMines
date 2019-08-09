package me.mmigas.listeners;

import me.mmigas.events.MineResetEvent;
import me.mmigas.files.LanguageManager;
import me.mmigas.mines.Flags;
import me.mmigas.mines.Mine;
import me.mmigas.utils.Utils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class MineResetListener implements Listener {

    @EventHandler
    public void OnMineResetEvent(MineResetEvent event) {
        Utils.broadcoast(LanguageManager.MINE_RESET_BROADCAST);
        Mine mine = event.getMine();

        if (mine.getFlags().contains(Flags.TeleportLocation)) {
            teleportPlayersTeleportLocation(mine);
        } else {
            teleportPlayersSurface(mine);
        }
    }

    private void teleportPlayersSurface(Mine mine) {
        List<Player> players = mine.playersInsideMine();
        int yCoord = mine.getMaxPosition().getY() + 1;

        for (Player player : players) {
            player.teleport(new Location(player.getLocation().getWorld(), player.getLocation().getBlockX(), yCoord, player.getLocation().getBlockZ()));
        }
    }

    private void teleportPlayersTeleportLocation(Mine mine) {
        List<Player> players = mine.playersInsideMine();
        for (Player player : players){
            player.teleport(mine.getTeleportLocation());
        }
    }

}
