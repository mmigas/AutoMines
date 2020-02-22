package me.mmigas.listeners;

import me.mmigas.gui.ClickAction;
import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(event.getView().getTopInventory().getHolder() instanceof Gui) {
            event.setCancelled(true);

            if(event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();

                ItemStack itemStack = event.getCurrentItem();
                if(itemStack == null || itemStack.getType() == Material.AIR){
                    return;
                }

                Gui gui = (Gui) event.getView().getTopInventory().getHolder();

                Item item = gui.getItem(event.getRawSlot());
                if(item == null) {
                    return;
                }

                for(ClickAction clickAction : item.getClickActions()) {
                    clickAction.execute(player);
                }
            }
        }

    }
}
