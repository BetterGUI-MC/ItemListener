package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.config.MessageConfig;
import me.hsgamer.bettergui.lib.core.bukkit.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Map;
import java.util.Optional;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public class ItemListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        String action = event.getAction().name();
        if (event.hasItem()) {
            Optional<Map.Entry<InteractiveItemStack, String>> optional = Main.getStorage()
                    .getMenu(event.getItem(), action.startsWith("LEFT_"), action.startsWith("RIGHT_"));
            if (optional.isPresent()) {
                Map.Entry<InteractiveItemStack, String> entry = optional.get();
                String menu = entry.getValue();
                if (getInstance().getMenuManager().contains(menu)) {
                    event.setCancelled(true);
                    getInstance().getMenuManager()
                            .openMenu(menu, player, entry.getKey().getArgs().toArray(new String[0]), false);
                } else {
                    MessageUtils.sendMessage(player, MessageConfig.MENU_NOT_FOUND.getValue());
                }
            }
        }
    }
}
