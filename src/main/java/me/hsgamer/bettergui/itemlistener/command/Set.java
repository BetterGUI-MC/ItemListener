package me.hsgamer.bettergui.itemlistener.command;

import me.hsgamer.bettergui.Permissions;
import me.hsgamer.bettergui.itemlistener.InteractiveItemStack;
import me.hsgamer.bettergui.itemlistener.Main;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;

import java.util.Arrays;

import static me.hsgamer.bettergui.BetterGUI.getInstance;
import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

public class Set extends BukkitCommand {

    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".setitemmenu", PermissionDefault.OP);
    private final Main main;

    public Set(Main main) {
        super("setitemmenu", "Bind an item to a menu",
                "/setitemmenu <menu> [isLeftClick] [isRightClick] [args]",
                Arrays.asList("itemmenu", "sim"));
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) {
            return false;
        }
        if (!(sender instanceof Player)) {
            sendMessage(sender, getInstance().getMessageConfig().playerOnly);
            return false;
        }

        if (args.length == 0) {
            sendMessage(sender, getInstance().getMessageConfig().menuRequired);
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
            main.getStorage().set(interactiveItemStack, args[0]);
            sendMessage(sender, getInstance().getMessageConfig().success);
        } else {
            sendMessage(sender, main.getMessageConfig().itemRequired);
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
