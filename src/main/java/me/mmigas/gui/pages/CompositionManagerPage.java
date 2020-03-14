package me.mmigas.gui.pages;

import me.mmigas.gui.Item;
import me.mmigas.gui.Menu;
import me.mmigas.gui.PageId;
import me.mmigas.mines.Mine;
import org.bukkit.Material;

public class CompositionManagerPage extends CompositionPages {
    public CompositionManagerPage(Menu menu, Mine mine) {
        super(menu, mine);
    }

    @Override
    public void setup() {
        updateContent();
        gui.setItem(gui.getInventory().getSize() - 9, new Item(Material.EMERALD_BLOCK, "Change Composition").addClickAction(player -> menu.switchPage(PageId.UPDATE_COMPOSITION)));
        gui.setItem(gui.getInventory().getSize() - 8, new Item(Material.PAPER, "Change Block Percentage").addClickAction(player -> menu.switchPage(PageId.CHOOSE_BLOCK_PAGE)));
        gui.setItem(gui.getInventory().getSize() - 1, new Item(Material.PAPER, "Main Menu").addClickAction(player -> menu.switchPage(PageId.MAIN)));
    }
}
