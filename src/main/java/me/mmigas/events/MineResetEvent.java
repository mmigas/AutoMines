package me.mmigas.events;

import me.mmigas.mines.Mine;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class MineResetEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private Mine mine;

    public MineResetEvent(Mine mine){
        this.mine = mine;
    }

    public Mine getMine(){
        return mine;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList(){
        return HANDLER_LIST;
    }


}
