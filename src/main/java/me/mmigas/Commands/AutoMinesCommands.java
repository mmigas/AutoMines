package me.mmigas.Commands;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import me.mmigas.AutoMines;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class AutoMinesCommands implements CommandExecutor {
	@Override
	public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
		if(command.getName().equalsIgnoreCase("create"))
			return new Create(AutoMines.getInstance().getWorldEditPlugin()).onCommand(commandSender, args);
		 else if(command.getName().equalsIgnoreCase("delete"))
			return new Delete().onCommand(commandSender, args);
		 else if(command.getName().equalsIgnoreCase("reset"))
			return  new Reset().onCommand(commandSender, args);
		 else if(command.getName().equalsIgnoreCase("setarea"))
		 	return new SetArea(AutoMines.getInstance().getWorldEditPlugin()).onCommand(commandSender, args);
		 else if(command.getName().equalsIgnoreCase("changeblock"))
		 	return new ChangeBlock().onCommand(commandSender, args);
		return false;
	}

}
