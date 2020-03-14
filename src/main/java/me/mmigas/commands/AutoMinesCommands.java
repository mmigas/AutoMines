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

    public AutoMinesCommands() {
        commandsList.add(new Create(getInstance().getWorldEditPlugin(), getInstance().getMineController()));
        commandsList.add(new Delete(getInstance().getMineController()));
        commandsList.add(new Help(getInstance().getMineController()));
        commandsList.add(new Info(getInstance().getMineController()));
        commandsList.add(new MenuCMD(getInstance().getMineController()));
        commandsList.add(new BlockGui(getInstance().getMineController()));
        commandsList.add(new BlockCMD(getInstance().getMineController()));
        commandsList.add(new RemovePlayer(getInstance().getMineController()));
        commandsList.add(new RemoveGui(getInstance().getMineController()));
        commandsList.add(new Reset(getInstance().getMineController()));
        commandsList.add(new SetArea(getInstance().getWorldEditPlugin(), getInstance().getMineController()));
        commandsList.add(new ChangeResetTimer(getInstance().getMineController()));
        commandsList.add(new SetTeleportLocation(getInstance().getMineController()));
        commandsList.add(new Timer(getInstance().getMineController()));
        commandsList.add(new MineList(getInstance().getMineController()));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, String[] args) {
        return execute(commandSender, args);
    }


    public void guiCommand(CommandSender commandSender, String[] args) {
        execute(commandSender, args);
    }

    private boolean execute(CommandSender commandSender, String[] args) {
        if(args.length == 0) {
            commandsList.get(2).onCommand(commandSender, args);
            return true;
        }

        for(CMD cmd : commandsList) {
            if(args[0].equalsIgnoreCase(cmd.getLabel())) {
                cmd.onCommand(commandSender, args);
                return true;
            }
        }
        LanguageManager.sendMessage(commandSender, LanguageManager.INVALID_COMMAND);
        return true;
    }
}
