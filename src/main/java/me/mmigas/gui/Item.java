package me.mmigas.gui;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Item {

    public final ItemStack itemStack;

    public final List<ClickAction> clickActions = new ArrayList<>();

    public Item(ItemStack itemStack){
        this.itemStack = itemStack;
    }

    public Item addClickAction(ClickAction clickAction){
        clickActions.add(clickAction);
        return this;
    }

    public List<ClickAction> getClickActions(){
        return clickActions;
    }

    public ItemStack getItemStack(){
        return itemStack;
    }

}
