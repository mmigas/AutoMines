package me.mmigas.gui;

import me.mmigas.AutoMines;
import me.mmigas.gui.pages.*;
import me.mmigas.mines.Mine;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.EnumMap;
import java.util.Map;

public class Menu {

    final Map<PageId, IPage> pages = new EnumMap<>(PageId.class);

    private PageId currentPage = PageId.MAIN;

    private final Player player;

    private final Mine mine;

    public Menu(Mine mine, Player player) {
        this.mine = mine;
        this.player = player;

        createPages();
    }

    private void createPages() {
        pages.put(PageId.MAIN, new MainPage(this, mine));
        pages.get(PageId.MAIN).setup();
        pages.put(PageId.COMPOSITIONMANAGER, new CompositionManagerPage(this, mine));
        pages.get(PageId.COMPOSITIONMANAGER).setup();
        pages.put(PageId.UPDATE_COMPOSITION, new UpdateCompositionPage(this, mine));
        pages.get(PageId.UPDATE_COMPOSITION).setup();
        pages.put(PageId.CHOOSE_BLOCK_PAGE, new ChooseBlockPage(this, mine));
        pages.get(PageId.CHOOSE_BLOCK_PAGE).setup();
        pages.put(PageId.PERCENTAGE_PAGE, new PercentagePage(this, mine));
        pages.get(PageId.PERCENTAGE_PAGE).setup();
        pages.put(PageId.RESET_PAGE, new ResetPage(this, mine));
        pages.get(PageId.RESET_PAGE).setup();
        pages.put(PageId.MINED_PERCENTAGE_PAGE, new MinedPercentagePage(this, mine));
        pages.get(PageId.MINED_PERCENTAGE_PAGE).setup();
    }

    public Inventory getCurrentInventory() {
        return pages.get(currentPage).getGui().getInventory();
    }

    public void switchPage(PageId pageId) {
        updateCurrentPage(pageId);
        switchPage();
    }

    public void updateCurrentPage(PageId pageId) {
        this.currentPage = pageId;
    }

    public void switchPage() {
        if(pages.get(currentPage) instanceof CompositionPages) {
            ((CompositionPages) pages.get(currentPage)).onPageOpen();
        }
        player.openInventory(getCurrentInventory());
    }

    public void sendCommand(Player player, String label) {
        String[] args = {label, mine.getName()};
        AutoMines.getInstance().getCommands().guiCommand(player, args);
        player.closeInventory();
    }

    public Map<PageId, IPage> getPages() {
        return pages;
    }

}
