package me.mmigas.utils;


import me.mmigas.language.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static Material getSolidBlockInPlayersHand(Player player) {
        ItemStack itemHolding = player.getInventory().getItemInMainHand();
        if (itemHolding.getType().equals(org.bukkit.Material.AIR)) {
            LanguageManager.send(player, LanguageManager.EMPTY_HAND);
            return null;
        }
        if (!itemHolding.getType().isBlock()) {
            LanguageManager.send(player, LanguageManager.NOT_BLOCK);
            return null;
        }
        if (!itemHolding.getType().isSolid()) {
            LanguageManager.send(player, LanguageManager.NOT_SOLID);
            return null;
        }

        return itemHolding.getType();
    }

    public static void broadcoast(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            LanguageManager.send(player, message);
        }
    }

}
