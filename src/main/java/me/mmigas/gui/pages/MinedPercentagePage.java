package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.IPage;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;

public class MinedPercentagePage implements IPage {
    private final Menu menu;
    private final Mine mine;
    private final Gui gui;

    public MinedPercentagePage(Menu menu, Mine mine) {
        this.menu = menu;
        this.mine = mine;
        gui = new Gui(mine.getName() + " Percentage settings", 3 * 9);
    }

    @Override
    public void setup() {

    }

    @Override
    public Gui getGui() {
        return gui;
    }
}
