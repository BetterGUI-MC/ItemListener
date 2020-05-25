package me.hsgamer.bettergui.itemlistener.command;

import static me.hsgamer.bettergui.util.CommonUtils.sendMessage;

import java.util.Arrays;
import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.config.impl.MessageConfig;
import me.hsgamer.bettergui.itemlistener.InteractiveItemStack;
import me.hsgamer.bettergui.itemlistener.Main;
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
        "/setitemmenu <menu> [isLeftClick] [isRightClick] [args]",
        Arrays.asList("itemmenu", "sim"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!(sender instanceof Player)) {
      sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
      return false;
    }
    if (!sender.hasPermission(PERMISSION)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }

    if (args.length <= 0) {
      sendMessage(sender, MessageConfig.MENU_REQUIRED.getValue());
      return false;
    }

    ItemStack itemStack = getItemInHand((Player) sender);
    if (!itemStack.getType().equals(Material.AIR)) {
      InteractiveItemStack interactiveItemStack = new InteractiveItemStack(
          itemStack);
      if (args.length >= 3) {
        interactiveItemStack.setLeftClick(Boolean.parseBoolean(args[1]));
        interactiveItemStack.setRightClick(Boolean.parseBoolean(args[2]));
      }
      if (args.length >= 4) {
        interactiveItemStack
            .setArgs(Arrays.asList(Arrays.copyOfRange(args, 3, args.length)));
      }
      Main.getStorage().set(interactiveItemStack, args[0]);
      sendMessage(sender, MessageConfig.SUCCESS.getValue());
    } else {
      sendMessage(sender, Main.ITEM_REQUIRED.getValue());
      return false;
    }
    return true;
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
