package me.hsgamer.bettergui.itemlistener.command;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Arrays;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.itemlistener.InteractiveItemStack;
import me.hsgamer.bettergui.itemlistener.Main;
import me.hsgamer.bettergui.util.TestCase;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

public class Set extends BukkitCommand {

  private static final Permission PERMISSION = Permissions
      .createPermission("bettergui.setitemmenu", null, PermissionDefault.OP);

  public Set() {
    super("setitemmenu", "Bind an item to a menu",
        "/setitemmenu <menu> [isLeftClick] [isRightClick]",
        Arrays.asList("itemmenu", "sim"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    return TestCase.create(sender)
        .setPredicate(commandSender -> commandSender instanceof Player)
        .setFailConsumer(commandSender ->
            sendMessage(commandSender,
                getInstance().getMessageConfig().get(DefaultMessage.PLAYER_ONLY)))
        .setSuccessNextTestCase(
            new TestCase<CommandSender>()
                .setPredicate(commandSender -> commandSender.hasPermission(PERMISSION))
                .setFailConsumer(commandSender -> sendMessage(commandSender,
                    getInstance().getMessageConfig().get(DefaultMessage.NO_PERMISSION)))
                .setSuccessNextTestCase(
                    new TestCase<CommandSender>()
                        .setPredicate(commandSender -> args.length > 0)
                        .setFailConsumer(commandSender -> sendMessage(commandSender,
                            getInstance().getMessageConfig().get(DefaultMessage.MENU_REQUIRED)))
                        .setSuccessConsumer(commandSender -> {
                          ItemStack itemStack = getItemInHand((Player) commandSender);
                          if (!itemStack.getType().equals(Material.AIR)) {
                            InteractiveItemStack interactiveItemStack = new InteractiveItemStack(
                                itemStack);
                            if (args.length > 3) {
                              interactiveItemStack.setLeftClick(Boolean.parseBoolean(args[1]));
                              interactiveItemStack.setRightClick(Boolean.parseBoolean(args[2]));
                            }
                            Main.getStorage().set(interactiveItemStack, args[0]);
                            sendMessage(commandSender, getInstance().getMessageConfig()
                                .get(DefaultMessage.SUCCESS));
                          } else {
                            sendMessage(commandSender, getInstance().getMessageConfig()
                                .get(String.class, "item-required",
                                    "&cYou should have an item in your hand"));
                          }
                        })
                )
        )
        .test();
  }

  @SuppressWarnings("deprecation")
  private ItemStack getItemInHand(Player player) {
    try {
      return player.getInventory().getItemInMainHand();
    } catch (Exception e) {
      return player.getInventory().getItemInHand();
    }
  }
}
