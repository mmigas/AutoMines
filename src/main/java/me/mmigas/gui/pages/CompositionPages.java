package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.IPage;
import me.mmigas.gui.Item;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class CompositionPages implements IPage {
    Gui gui;
    Menu menu;
    Mine mine;
    int compositionRows;

    public CompositionPages(Menu menu, Mine mine) {
        this.menu = menu;
        this.mine = mine;
        compositionRows = mine.getContent().size() / 9 + 1;
        this.gui = new Gui(mine.getName() + " composition", compositionRows * 9 + 9);
    }

    public void updateContent() {
        for(int i = 0; i < compositionRows * 9; i++) {
            Item item = new Item(Material.AIR);
            gui.setItem(i, item);
        }

        for(int i = 0; i < mine.getContent().size(); i++) {
            Item item = new Item(mine.getContent().get(i).getMaterial(), String.valueOf(mine.getContent().get(i).getPercentage()));
            gui.setItem(i, item);
        }
    }

    public void onPageOpen() {
        updateContent();
    }

    @NotNull
    List<Material> getMaterialsFromInventory(Inventory inventory) {
        ArrayList<Material> materials = new ArrayList<>();
        ItemStack[] content = inventory.getContents();
        for(ItemStack itemStack : content) {
            if(itemStack != null && itemStack.getType() != Material.AIR && itemStack.getType() != Material.PAPER) {
                materials.add(itemStack.getType());
            }
        }
        return materials;
    }

    @Override
    public Gui getGui() {
        return gui;
    }
}
