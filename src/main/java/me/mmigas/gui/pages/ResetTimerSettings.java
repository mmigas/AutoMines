package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import me.mmigas.gui.Menu;
import me.mmigas.gui.PageId;
import me.mmigas.mines.Mine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ResetTimerSettings extends Page {

    private long resetCooldown;

    public ResetTimerSettings(Menu menu, Mine mine) {
        super(menu, mine);
        gui = new Gui(mine.getName() + " Reset Timer settings", 9 * 6);
        this.resetCooldown = mine.getResetCooldown() / 1000;
    }

    @Override
    public void setup() {
        gui.setItem(4, new Item(Material.PAPER, "Info", timerFormate()));

        gui.setItem(0, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:01")
                .addClickAction(player -> subtractResetCooldown(1)).addClickAction(this::updateGui));
        gui.setItem(1, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:05")
                .addClickAction(player -> subtractResetCooldown(5)).addClickAction(this::updateGui));
        gui.setItem(2, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:10")
                .addClickAction(player -> subtractResetCooldown(10)).addClickAction(this::updateGui));
        gui.setItem(3, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:15")
                .addClickAction(player -> subtractResetCooldown(15)).addClickAction(this::updateGui));
        gui.setItem(9, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:20")
                .addClickAction(player -> subtractResetCooldown(20)).addClickAction(this::updateGui));
        gui.setItem(1 + 9, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:25")
                .addClickAction(player -> subtractResetCooldown(25)).addClickAction(this::updateGui));
        gui.setItem(2 + 9, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:30")
                .addClickAction(player -> subtractResetCooldown(30)).addClickAction(this::updateGui));
        gui.setItem(3 + 9, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:00:35")
                .addClickAction(player -> subtractResetCooldown(35)).addClickAction(this::updateGui));
        gui.setItem(9 * 2, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:01:00")
                .addClickAction(player -> subtractResetCooldown(60L)).addClickAction(this::updateGui));
        gui.setItem(1 + 9 * 2, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:05:00")
                .addClickAction(player -> subtractResetCooldown(5 * 60L)).addClickAction(this::updateGui));
        gui.setItem(2 + 9 * 2, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:10:00")
                .addClickAction(player -> subtractResetCooldown(10 * 60L)).addClickAction(this::updateGui));
        gui.setItem(3 + 9 * 2, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:15:00")
                .addClickAction(player -> subtractResetCooldown(15 * 60L)).addClickAction(this::updateGui));
        gui.setItem(9 * 3, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:20:00")
                .addClickAction(player -> subtractResetCooldown(20 * 60L)).addClickAction(this::updateGui));
        gui.setItem(1 + 9 * 3, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:25:00")
                .addClickAction(player -> subtractResetCooldown(25 * 60L)).addClickAction(this::updateGui));
        gui.setItem(2 + 9 * 3, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:30:00")
                .addClickAction(player -> subtractResetCooldown(30 * 60L)).addClickAction(this::updateGui));
        gui.setItem(3 + 9 * 3, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-00:35:00")
                .addClickAction(player -> subtractResetCooldown(35 * 60L)).addClickAction(this::updateGui));
        gui.setItem(9 * 4, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-01:00:00")
                .addClickAction(player -> subtractResetCooldown(60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(1 + 9 * 4, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-05:00:00")
                .addClickAction(player -> subtractResetCooldown(5 * 60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(2 + 9 * 4, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-10:00:00")
                .addClickAction(player -> subtractResetCooldown(10 * 60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(3 + 9 * 4, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-15:00:00")
                .addClickAction(player -> subtractResetCooldown(15 * 60L * 60L)).addClickAction(this::updateGui));

        gui.setItem(5, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:01")
                .addClickAction(player -> addResetCooldown(1)).addClickAction(this::updateGui));
        gui.setItem(6, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:05")
                .addClickAction(player -> addResetCooldown(5)).addClickAction(this::updateGui));
        gui.setItem(7, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:10")
                .addClickAction(player -> addResetCooldown(10)).addClickAction(this::updateGui));
        gui.setItem(8, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:15")
                .addClickAction(player -> addResetCooldown(15)).addClickAction(this::updateGui));
        gui.setItem(5 + 9, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:20")
                .addClickAction(player -> addResetCooldown(20)).addClickAction(this::updateGui));
        gui.setItem(6 + 9, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:25")
                .addClickAction(player -> addResetCooldown(25)).addClickAction(this::updateGui));
        gui.setItem(7 + 9, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:30")
                .addClickAction(player -> addResetCooldown(30)).addClickAction(this::updateGui));
        gui.setItem(8 + 9, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:00:35")
                .addClickAction(player -> addResetCooldown(35)).addClickAction(this::updateGui));
        gui.setItem(5 + 9 * 2, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:01:00")
                .addClickAction(player -> addResetCooldown(60L)).addClickAction(this::updateGui));
        gui.setItem(6 + 9 * 2, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:05:00")
                .addClickAction(player -> addResetCooldown(5 * 60L)).addClickAction(this::updateGui));
        gui.setItem(7 + 9 * 2, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:10:00")
                .addClickAction(player -> addResetCooldown(10 * 60L)).addClickAction(this::updateGui));
        gui.setItem(8 + 9 * 2, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:15:00")
                .addClickAction(player -> addResetCooldown(15 * 60L)).addClickAction(this::updateGui));
        gui.setItem(5 + 9 * 3, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:20:00")
                .addClickAction(player -> addResetCooldown(20 * 60L)).addClickAction(this::updateGui));
        gui.setItem(6 + 9 * 3, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:25:00")
                .addClickAction(player -> addResetCooldown(25 * 60L)).addClickAction(this::updateGui));
        gui.setItem(7 + 9 * 3, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:30:00")
                .addClickAction(player -> addResetCooldown(30 * 60L)).addClickAction(this::updateGui));
        gui.setItem(8 + 9 * 3, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+00:35:00")
                .addClickAction(player -> addResetCooldown(35 * 60L)).addClickAction(this::updateGui));
        gui.setItem(5 + 9 * 4, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+01:00:00")
                .addClickAction(player -> addResetCooldown(60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(6 + 9 * 4, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+05:00:00")
                .addClickAction(player -> addResetCooldown(5 * 60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(7 + 9 * 4, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+10:00:00")
                .addClickAction(player -> addResetCooldown(10 * 60L * 60L)).addClickAction(this::updateGui));
        gui.setItem(8 + 9 * 4, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+15:00:00")
                .addClickAction(player -> addResetCooldown(15 * 60L * 60L)).addClickAction(this::updateGui));

        gui.setItem(3 + 9 * 5, new Item(Material.PAPER, ChatColor.BLUE + "Save").addClickAction(this::saveCooldownReset));
        gui.setItem(5 + 9 * 5, new Item(Material.PAPER, ChatColor.RED + "Back").addClickAction(player -> menu.switchPage(PageId.RESET_PAGE)));
    }

    private void subtractResetCooldown(long cooldown) {
        if((resetCooldown - cooldown) >= 0) {
            resetCooldown -= cooldown;
        } else {
            resetCooldown = 0;
        }
    }

    private void addResetCooldown(long cooldown) {
        resetCooldown += cooldown;
    }

    private void updateGui(Player player) {
        gui.forceItem(player, 4, new Item(Material.PAPER, "Info", timerFormate()));
    }

    private void saveCooldownReset(Player player) {
        AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"changetimer", mine.getName(), String.valueOf(resetCooldown * 1000)});
    }

    private List<String> timerFormate() {
        List<String> lore = new ArrayList<>();
        int secods = (int) (resetCooldown % 60);
        int minutes = (int) ((resetCooldown / 60) % 60);
        int hours = (int) ((resetCooldown / 60 / 60) % 60);
        lore.add(String.format("Cooldown: %s:%s:%s", hours, minutes, secods));
        return lore;
    }

}


