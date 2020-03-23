package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.gui.*;
import me.mmigas.mines.Mine;
import org.bukkit.Material;

public class MainPage extends Page {
    public MainPage(Menu menu, Mine mine) {
        super(menu, mine);
        this.gui = new Gui(mine.getName() + "'s Menu", 5 * 9);
    }

    @Override
    public void setup() {
        gui.setItem(1 + 9, new Item(Material.DIAMOND_BLOCK, "Composition").addClickAction(player -> menu.switchPage(PageId.COMPOSITIONMANAGER)));
        gui.setItem(3 + 9, new Item(Material.WOODEN_AXE, "Set Area")
                .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"area"})));
        gui.setItem(5 + 9, new Item(Material.BEACON, "Set teleport")
                .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"tp"})));
        gui.setItem(7 + 9, new Item(Material.PAPER, "Info").addClickAction(player -> menu.sendCommand(player, "info")));
        gui.setItem(2 + 9 * 3, new Item(Material.REDSTONE, "Reset").addClickAction(player -> menu.switchPage(PageId.RESET_PAGE)));
    }
}
