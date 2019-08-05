package me.mmigas.commands;

import me.mmigas.language.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import static me.mmigas.AutoMines.*;

public class AutoMinesCommands implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
            return new Help().onCommand(commandSender);
        else if (args[0].equalsIgnoreCase("create"))
            return new Create(getInstance().getWorldEditPlugin(), getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("delete"))
            return new Delete(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("reset"))
            return new Reset(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("setarea"))
            return new SetArea(getInstance().getWorldEditPlugin(), getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("add"))
            return new AddBlock(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("remove"))
            return new RemoveBlock(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("resettimer"))
            return new ResetTimer(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("info"))
            return new Info(getInstance().getMineController()).onCommand(commandSender, args);
        else
            LanguageManager.send(commandSender, LanguageManager.INVALID_COMMAND);
        return true;

    }

}
