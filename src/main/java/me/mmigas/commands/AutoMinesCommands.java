package me.mmigas.commands;

import me.mmigas.commands.subcommands.*;
import me.mmigas.files.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static me.mmigas.AutoMines.getInstance;

public class AutoMinesCommands implements CommandExecutor {

    private List<CMD> commandsList = new ArrayList<>();

    public AutoMinesCommands(){
        commandsList.add(new AddBlock(getInstance().getMineController()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        for(CMD cmd : commandsList) {
            if(args[0].equalsIgnoreCase(cmd.getLabel())) {
                cmd.onCommand(commandSender, args);
            }
        }


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
        else if (args[0].equalsIgnoreCase("timer"))
            return new SetResetTimer(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("start"))
            return new StartResetTimer(getInstance().getMineController()).onCommand(commandSender, args);
        else if (args[0].equalsIgnoreCase("info"))
            return new Info(getInstance().getMineController()).onCommand(commandSender, args);
        else if(args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("teleport"))
            return new SetTeleportLocation(getInstance().getMineController()).onCommand(commandSender, args);
        else if(args[0].equalsIgnoreCase("menu"))
            return new MenuCMD(getInstance().getMineController()).onCommand(commandSender, args);
        else
            LanguageManager.sendMessage(commandSender, LanguageManager.INVALID_COMMAND);
        return true;

    }

}
