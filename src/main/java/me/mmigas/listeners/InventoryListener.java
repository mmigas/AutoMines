package me.mmigas.listeners;

import me.mmigas.gui.IClickAction;
import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if(event.getView().getTopInventory().getHolder() instanceof Gui) {

        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getView().getTopInventory().getHolder() instanceof Gui && event.getWhoClicked() instanceof Player) {

            Player player = (Player) event.getWhoClicked();
            ItemStack itemStack = event.getCurrentItem();

            if(itemStack == null || itemStack.getType() == Material.AIR) {
                return;
            }

            if(event.getView().getTitle().contains("Add|Remove") && itemStack.getType() != Material.PAPER) {
                return;
            }
            event.setCancelled(true);

            Gui gui = (Gui) event.getView().getTopInventory().getHolder();
            Item item = gui.getItem(event.getRawSlot());
            if(item == null) {
                return;
            }

            for(IClickAction IClickAction : item.getIClickActions()) {
                IClickAction.execute(player);
            }
        }
    }
}

