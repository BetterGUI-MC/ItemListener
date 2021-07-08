package me.hsgamer.bettergui.itemlistener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.itemlistener.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Collections;

import static me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils.sendMessage;

public class Remove extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".removeitemmenu", PermissionDefault.OP);

    public Remove() {
        super("removeitemmenu", "Remove the binding to the menu", "/removeitemmenu <menu>",
                Collections.singletonList("rim"));
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }
        if (strings.length > 0) {
            Main.getStorage().remove(strings[0]);
            sendMessage(commandSender, MessageConfig.SUCCESS.getValue());
        } else {
            sendMessage(commandSender, MessageConfig.MENU_REQUIRED.getValue());
            return false;
        }
        return true;
    }
}
