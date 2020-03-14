package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.files.LanguageManager;
import me.mmigas.gui.*;
import me.mmigas.mines.Container;
import me.mmigas.mines.Mine;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PercentagePage implements IPage {
    private final Gui gui;
    private int percentage = -1;
    private Menu menu;
    private Material material;
    private Mine mine;

    public PercentagePage(Menu menu, Mine mine) {
        this.menu = menu;
        this.mine = mine;
        gui = new Gui(mine.getName() + " Change percentage", 27);
    }

    @Override
    public void setup() {
        gui.setItem(9, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-1").addClickAction(player -> changePercentage(player, -1)));
        gui.setItem(10, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-5").addClickAction(player -> changePercentage(player, -5)));
        gui.setItem(11, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-10").addClickAction(player -> changePercentage(player, -10)));
        gui.setItem(12, new Item(Material.REDSTONE_BLOCK, ChatColor.RED + "-50").addClickAction(player -> changePercentage(player, -50)));
        gui.setItem(13, new Item(Material.BLACK_STAINED_GLASS, ChatColor.BLUE + String.valueOf(percentage)));
        gui.setItem(14, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+50").addClickAction(player -> changePercentage(player, 50)));
        gui.setItem(15, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+10").addClickAction(player -> changePercentage(player, 10)));
        gui.setItem(16, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+5").addClickAction(player -> changePercentage(player, 5)));
        gui.setItem(17, new Item(Material.EMERALD_BLOCK, ChatColor.GREEN + "+1").addClickAction(player -> changePercentage(player, 1)));
        gui.setItem(21, new Item(Material.PAPER, ChatColor.RED + "Save").addClickAction(this::savePercentage));
        gui.setItem(23, new Item(Material.PAPER, ChatColor.RED + "Back").addClickAction(player -> menu.switchPage(PageId.MAIN)));
    }

    public void changePercentage(Player player, int percentage) {
        if(this.percentage + percentage >= 0 && this.percentage + percentage <= 100) {
            this.percentage += percentage;
            gui.forceItem(player, 13, new Item(Material.BLACK_STAINED_GLASS, ChatColor.BLUE + String.valueOf(this.percentage)));
        }
    }

    private void savePercentage(Player player) {
        if(!AutoMines.getInstance().getMineController().changeBlock(mine, material, percentage)) {
            LanguageManager.sendKey(player, LanguageManager.PERCENTAGE_GREATER_THAN_100);
            return;
        }
        LanguageManager.sendKey(player, LanguageManager.BLOCK_ADDED);
    }

    public void setMaterial(Player player, @NotNull Container container) {
        this.material = container.getMaterial();
        gui.forceItem(player, 4, new Item(container.getMaterial(), ChatColor.BLUE + String.valueOf(container.getPercentage())));
        percentage = container.getPercentage();
    }

    @Override
    public Gui getGui() {
        return gui;
    }
}
