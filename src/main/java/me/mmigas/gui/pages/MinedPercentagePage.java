package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;

public class MinedPercentagePage extends Page {
    public MinedPercentagePage(Menu menu, Mine mine) {
        super(menu, mine);
        gui = new Gui(mine.getName() + " Percentage settings", 3 * 9);
    }

    @Override
    public void setup() {

    }
}
