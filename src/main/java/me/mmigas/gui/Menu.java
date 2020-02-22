package me.mmigas.gui;

import me.mmigas.gui.pages.IPage;
import me.mmigas.mines.Mine;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    final List<IPage> pages = new ArrayList<>();

    private int currentPage = 0;

    private final Player player;

    private final Mine mine;

    public Menu(Mine mine, Player player) {
        this.mine = mine;
        this.player = player;
        createPages();
    }

    private void createPages() {


    }

    public Inventory getCurrentInventory() {
        return pages.get(currentPage).getGui().getInventory();
    }

    public void updateCurrentPage(int newPageIndex) {
       this.currentPage = newPageIndex;
    }

    public void switchPage(){
        player.openInventory(getCurrentInventory());
    }

    public List<IPage> getPages() {
        return pages;
    }

}
