package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import me.mmigas.gui.Menu;
import me.mmigas.gui.PageId;
import me.mmigas.mines.Mine;
import org.bukkit.Material;

public class ChooseBlockPage extends CompositionPages {
    public ChooseBlockPage(Menu menu, Mine mine) {
        super(menu, mine);
        this.gui = new Gui(mine.getName() + " choose block", compositionRows * 9 + 9);
    }

    @Override
    public void setup() {
        Item item = new Item(Material.PAPER, "Back").addClickAction(player -> menu.switchPage(PageId.COMPOSITIONMANAGER));
        gui.setItem(compositionRows * 9 + 8, item);
    }

    @Override
    public void onPageOpen() {
        for(int i = 0; i < mine.getContent().size(); i++) {
            int finalI = i;
            Item item = new Item(mine.getContent().get(i).getMaterial(), String.valueOf(mine.getContent().get(i).getPercentage()))
                    .addClickAction(player -> ((PercentagePage) menu.getPages().get(PageId.PERCENTAGE_PAGE)).setMaterial(player, mine.getContent().get(finalI)))
                    .addClickAction(player -> menu.switchPage(PageId.PERCENTAGE_PAGE));
            gui.setItem(i, item);
        }
    }
}
