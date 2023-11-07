package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.Permissions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;
import teammt.commanditems.main.CommandItems;
import teammt.commanditems.managers.CommandItemManager;
import teammt.commanditems.managers.model.CommandItem;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

public class ConvertItemCommand extends Command {
    private static final Permission PERMISSION = new Permission(Permissions.PREFIX + ".convertitemmenu", PermissionDefault.OP);
    private final Main main;

    public ConvertItemCommand(Main main) {
        super("convertitemmenu");
        this.main = main;
        setPermission(PERMISSION.getName());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!testPermission(commandSender)) {
            return false;
        }

        CommandItemManager manager;
        try {
            CommandItems plugin = JavaPlugin.getPlugin(CommandItems.class);
            Field field = plugin.getClass().getDeclaredField("itemManager");
            field.setAccessible(true);
            manager = (CommandItemManager) field.get(plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        int count = 0;
        for (Map.Entry<InteractiveItemStack, String> entry : main.getStorage().getItemToMenuMap().entrySet()) {
            ItemStack itemStack = entry.getKey().getItemStack();

            CommandItem commandItem = new CommandItem(itemStack);
            commandItem.setByConsole(Collections.singletonList("openmenu " + entry.getValue() + " " + String.join(" ", entry.getKey().getArgs())));

            manager.saveItem(commandItem, "bettergui-" + count++);
        }

        commandSender.sendMessage("Converted " + count + " items");
        return true;
    }
}
