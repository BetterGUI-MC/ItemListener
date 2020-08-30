package me.hsgamer.bettergui.itemlistener.command;

import static me.hsgamer.bettergui.util.MessageUtils.sendMessage;

import java.util.Collections;
import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.itemlistener.Main;
import me.hsgamer.bettergui.util.PermissionUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Remove extends BukkitCommand {

  private static final Permission PERMISSION = PermissionUtils
      .createPermission("bettergui.removeitemmenu", null, PermissionDefault.OP);

  public Remove() {
    super("removeitemmenu", "Remove the binding to the menu", "/removeitemmenu <menu>",
        Collections.singletonList("rim"));
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (!commandSender.hasPermission(PERMISSION)) {
      sendMessage(commandSender, MessageConfig.NO_PERMISSION.getValue());
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
