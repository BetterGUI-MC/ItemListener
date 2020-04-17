package me.hsgamer.bettergui.itemlistener;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.config.impl.MessageConfig.DefaultMessage;
import me.hsgamer.bettergui.util.CommonUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

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
          CommonUtils.sendMessage(player,
              getInstance().getMessageConfig().get(DefaultMessage.MENU_NOT_FOUND));
        }
      }
    }
  }
}
