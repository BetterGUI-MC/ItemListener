package me.hsgamer.bettergui.itemlistener.command;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Collections;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.itemlistener.Main;
import me.hsgamer.bettergui.util.TestCase;
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
    return TestCase.create(commandSender)
        .setPredicate(commandSender1 -> commandSender1.hasPermission(PERMISSION))
        .setFailConsumer(commandSender1 -> sendMessage(commandSender1,
            getInstance().getMessageConfig().get(DefaultMessage.NO_PERMISSION)))
        .setSuccessConsumer(commandSender1 -> {
          if (strings.length > 0) {
            Main.getStorage().remove(strings[0]);
            sendMessage(commandSender1, getInstance().getMessageConfig()
                .get(DefaultMessage.SUCCESS));
          } else {
            sendMessage(commandSender1,
                getInstance().getMessageConfig().get(DefaultMessage.MENU_REQUIRED));
          }
        }).test();
  }
}
