package me.mmigas.listeners;

import me.mmigas.AutoMines;
import me.mmigas.mines.MineController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    private MineController mineController = AutoMines.getInstance().getMineController();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        for (int i = 0; i < mineController.getMinesList().size(); i++) {
            if (mineController.getMinesList().get(i).containsBlock(event.getBlock().getLocation())) {
                mineController.getMinesList().get(i).updateTotalPercentage();
                return;
            }
        }
    }
}
