package me.hsgamer.bettergui.itemlistener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.itemlistener.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

public class Remove extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".removeitemmenu", PermissionDefault.OP);
    private final Main main;

    public Remove(Main main) {
        super("removeitemmenu", "Remove the binding to the menu", "/removeitemmenu <menu>",
                Collections.singletonList("rim"));
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }
        if (strings.length == 0) {
            sendMessage(commandSender, getInstance().getMessageConfig().menuRequired);
            return false;
        }
        main.getStorage().remove(strings[0]);
        sendMessage(commandSender, getInstance().getMessageConfig().success);
        return true;
    }
}
