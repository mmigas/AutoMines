package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.gui.*;
import me.mmigas.mines.Flags;
import me.mmigas.mines.Mine;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class ResetPage extends Page {

    public ResetPage(Menu menu, Mine mine) {
        super(menu, mine);
        gui = new Gui(mine.getName() + " Reset settings page", 5 * 9);
    }

    @Override
    public void setup() {
        gui.setItem(4 + 9, new Item(Material.REDSTONE, ChatColor.RED + "Reset")
                .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"reset", mine.getName()})));
        gui.setItem(3 + 9 * 2, new Item(Material.CLOCK, ChatColor.GREEN + "Reset Timer settings")
                .addClickAction(player -> menu.switchPage(PageId.RESET_TIMER_SETTINGS)));
        updateButton(7 + 9 * 2, Flags.TimeReset);
        gui.setItem(3 + 9 * 3, new Item(Material.DIAMOND_PICKAXE, ChatColor.GREEN + "Reset Percentage settings")
                .addClickAction(player -> menu.switchPage(PageId.RESET_PERCENTAGE_SETTINGS)));
        gui.setItem(7 + 9 * 3, new Item(Material.REDSTONE, ChatColor.RED + "Reset by percentage"));
    }

    private void updateButton(int position, Flags flag) {
        if(mine.getFlags().contains(flag)) {
            gui.setItem(position, new Item(Material.EMERALD, ChatColor.RED + "Start/Stop Reset Timer")
                    .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"timer", mine.getName()}))
                    .addClickAction(player -> updateButton(position, flag)));
        } else {
            gui.setItem(7 + 9 * 2, new Item(Material.REDSTONE, ChatColor.GREEN + "Start/Stop Reset Timer")
                    .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"timer", mine.getName()}))
                    .addClickAction(player -> updateButton(position, flag)));
        }
    }
}
