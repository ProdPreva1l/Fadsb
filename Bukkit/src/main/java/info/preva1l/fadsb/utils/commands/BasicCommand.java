package info.preva1l.fadsb.utils.commands;

import info.preva1l.fadsb.Fadsb;
import info.preva1l.fadsb.user.CommandUser;
import info.preva1l.fadsb.user.ConsoleUser;
import info.preva1l.fadsb.utils.TaskManager;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class BasicCommand {
    public Fadsb plugin;
    private final Command info;
    private final List<BasicSubCommand> subCommands;

    public BasicCommand(Fadsb plugin) {
        this.plugin = plugin;
        this.info = Arrays.stream(this.getClass().getConstructors()).filter(method -> method.getAnnotation(Command.class) != null).map(method -> method.getAnnotation(Command.class)).findFirst().orElse(null);
        this.subCommands = new ArrayList<>();

        if (info == null) {
            throw new RuntimeException("BasicCommand constructor must be annotated with @Command");
        }
    }

    public abstract void execute(CommandUser sender, String[] args);

    public List<String> tabComplete(CommandUser sender, String[] args) {
        return new ArrayList<>();
    }

    /**
     * Handles subcommand execution easily.
     * @param sender command sender
     * @param args command args
     * @return true if the subcommand was executed,
     * false if the sender does not have permission or if the sender was console on a player only command
     */
    public boolean subCommandExecutor(CommandUser sender, String[] args) {
        for (BasicSubCommand subCommand : subCommands) {
            if (args[0].equalsIgnoreCase(subCommand.getInfo().name())
                    || Arrays.stream(subCommand.getInfo().aliases()).toList().contains(args[0])) {

                if (subCommand.getInfo().inGameOnly() && sender instanceof ConsoleUser) {
                    sender.sendMessage("MUST BE PLAYER LOCALE");
                    return false;
                }

                if (!subCommand.getInfo().permission().isEmpty() && !sender.hasPermission(subCommand.getInfo().permission())) {
                    sender.sendMessage("NO PERMISSION LOCALE");
                    return false;
                }

                if (subCommand.getInfo().async()) {
                    TaskManager.Async.run(() -> subCommand.execute(sender, removeFirstElement(args)));
                } else {
                    TaskManager.Sync.run(() -> subCommand.execute(sender, removeFirstElement(args)));
                }
                return true;
            }
        }
        return false;
    }

    private String[] removeFirstElement(String[] array) {
        if (array == null || array.length == 0) {
            return new String[]{};
        }

        String[] newArray = new String[array.length - 1];
        System.arraycopy(array, 1, newArray, 0, array.length - 1);

        return newArray;
    }
}