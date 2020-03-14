package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.IPage;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;

public class ResetPage implements IPage {
    private Menu menu;
    private Mine mine;
    private final Gui gui;

    public ResetPage(Menu menu, Mine mine) {
        this.menu = menu;
        this.mine = mine;
        gui = new Gui(mine.getName() + " Reset settings page", 3 * 9);
    }

    @Override
    public void setup() {
        
    }

    @Override
    public Gui getGui() {
        return gui;
    }
}
