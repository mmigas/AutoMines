package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class MainPage implements IPage{

    private final Gui gui;

    public MainPage(Gui gui){
        this.gui = gui;
    }

    @Override
    public void setup() {
        gui.setItem(3, new Item(Material.PAPER).addClickAction(player -> ));
    }

    @Override
    public Gui getGui() {
        return gui;
    }
}
