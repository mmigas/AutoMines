package me.mmigas.gui.pages;

import me.mmigas.AutoMines;
import me.mmigas.gui.Gui;
import me.mmigas.gui.Item;
import me.mmigas.gui.Menu;
import me.mmigas.gui.PageId;
import me.mmigas.mines.Container;
import me.mmigas.mines.Mine;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class UpdateCompositionPage extends CompositionPages {

    public UpdateCompositionPage(Menu menu, Mine mine) {
        super(menu, mine);
        compositionRows = 5;
        this.gui = new Gui(mine.getName() + " Add|Remove", compositionRows * 9 + 9);
    }

    @Override
    public void setup() {
        updateContent();
        gui.setItem(compositionRows * 9 + 4, new Item(Material.PAPER, "Save").addClickAction(Player::updateInventory).addClickAction(this::saveComposition));
        gui.setItem(compositionRows * 9 + 8, new Item(Material.PAPER, "Back").addClickAction(player -> menu.switchPage(PageId.COMPOSITIONMANAGER)));
    }

    private void saveComposition(Player player) {
        List<Material> materials = getMaterialsFromInventory(player.getOpenInventory().getTopInventory());
        List<Material> materialsToRemove = new ArrayList<>();

        for(Container container : mine.getContent()) {
            if(!materials.contains(container.getMaterial())) {
                materialsToRemove.add(container.getMaterial());
            }
        }

        for(Material material : materialsToRemove) {
            AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"removegui", mine.getName(), material.toString()});
        }

        for(Material material : materials) {
            if(!AutoMines.getInstance().getMineController().containsMateiral(mine, material)) {
                AutoMines.getInstance().getCommands().guiCommand(player, new String[]{"blockgui", mine.getName(), material.toString(), String.valueOf(0)});
            }
        }

        player.sendMessage("Content updated");
    }
}
