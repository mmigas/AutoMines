package me.mmigas.listeners;

import me.mmigas.AutoMines;
import me.mmigas.events.MineCreateEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class MineCreateListener implements Listener {

    @EventHandler
    public void onMineCreateEvent(MineCreateEvent event){
        AutoMines.getInstance().getMineStorage().saveMine(event.getMine());
    }
}
