package me.hsgamer.bettergui.itemlistener.command;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Collections;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.itemlistener.Main;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Remove extends BukkitCommand {

  private static final Permission PERMISSION = Permissions
      .createPermission("bettergui.removeitemmenu", null, PermissionDefault.OP);

  public Remove() {
    super("removeitemmenu", "Remove the binding to the menu", "/removeitemmenu <menu>",
        Collections.singletonList("rim"));
  }

  @Override
  public boolean execute(CommandSender commandSender, String s, String[] strings) {
    if (!commandSender.hasPermission(PERMISSION)) {
      sendMessage(commandSender,
          getInstance().getMessageConfig().get(DefaultMessage.NO_PERMISSION));
      return false;
    }
    if (strings.length > 0) {
      Main.getStorage().remove(strings[0]);
      sendMessage(commandSender, getInstance().getMessageConfig()
          .get(DefaultMessage.SUCCESS));
    } else {
      sendMessage(commandSender,
          getInstance().getMessageConfig().get(DefaultMessage.MENU_REQUIRED));
      return false;
    }
    return true;
  }
}
