package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.gui.*;
import me.mmigas.mines.Mine;
import org.bukkit.Material;

public class MainPage implements IPage {

    private final Gui gui;
    private Menu menu;

    public MainPage(Menu menu, Mine mine) {
        this.gui = new Gui(mine.getName() + "'s Menu", 5 * 9);
        this.menu = menu;
    }

    @Override
    public void setup() {
        gui.setItem(1 + 9, new Item(Material.DIAMOND_BLOCK, "Composition").addClickAction(player -> menu.switchPage(PageId.COMPOSITIONMANAGER)));
        gui.setItem(3 + 9, new Item(Material.WOODEN_AXE, "Set Area")
                .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"area"})));
        gui.setItem(5 + 9, new Item(Material.BEACON, "Set teleport")
                .addClickAction(player -> AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"tp"})));
        gui.setItem(7 + 9, new Item(Material.PAPER, "Info").addClickAction(player -> menu.sendCommand(player, "info")));
        gui.setItem(2 + 9 * 2, new Item(Material.CLOCK, " Timer").addClickAction(player -> menu.switchPage(PageId.RESET_PAGE)));
        gui.setItem(2 + 9 * 2, new Item(Material.CLOCK, " Percentage").addClickAction(player -> menu.switchPage(PageId.MINED_PERCENTAGE_PAGE)));

    }

    @Override
    public Gui getGui() {
        return gui;
    }

}
