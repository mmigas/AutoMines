package me.mmigas.gui.pages;

import me.mmigas.gui.Gui;
import me.mmigas.gui.Menu;
import me.mmigas.mines.Mine;

public abstract class Page {
    final Menu menu;
    Gui gui;
    final Mine mine;

    public Page(Menu menu, Mine mine) {
        this.menu = menu;
        this.mine = mine;
    }

    public abstract void setup();

    public Gui getGui() {
        return gui;
    }
}
