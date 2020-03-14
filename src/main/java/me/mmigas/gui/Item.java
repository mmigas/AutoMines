package me.mmigas.gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public final ItemStack itemStack;

    public final List<IClickAction> IClickActions = new ArrayList<>();

    public Item(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public Item(Material material, String itemName) {
        this.itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(itemName);
        itemStack.setItemMeta(meta);
    }

    public Item(Material material, String itemName, List<String> lore) {
        this.itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(itemName);
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
    }

    public Item addClickAction(IClickAction IClickAction) {
        IClickActions.add(IClickAction);
        return this;
    }

    public List<IClickAction> getIClickActions() {
        return IClickActions;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
