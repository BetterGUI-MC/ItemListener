package me.hsgamer.bettergui.itemlistener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ItemStorage {

  private Map<InteractiveItemStack, String> itemToMenuMap = new HashMap<>();
  private Addon addon;
  private FileConfiguration config;

  public ItemStorage(Addon addon) {
    this.addon = addon;
    this.config = addon.getConfig();
    load();
  }

  @SuppressWarnings("unchecked")
  public void load() {
    config.getKeys(false).forEach(s -> config.getMapList(s)
        .forEach(map -> itemToMenuMap
            .put(InteractiveItemStack.deserialize((Map<String, Object>) map), s)));
  }

  public void save() {
    Map<String, List<Map<String, Object>>> map = new HashMap<>();
    itemToMenuMap.forEach((item, s) -> {
      if (!map.containsKey(s)) {
        map.put(s, new ArrayList<>());
      }
      map.get(s).add(item.serialize());
    });
    map.forEach(config::set);
    addon.saveConfig();
  }

  public void set(InteractiveItemStack item, String menu) {
    itemToMenuMap.put(item, menu);
  }

  public void remove(InteractiveItemStack item) {
    itemToMenuMap.remove(item);
  }

  @Nullable
  public String getMenu(ItemStack item, boolean leftClick, boolean rightClick) {
    for (Map.Entry<InteractiveItemStack, String> entry : itemToMenuMap.entrySet()) {
      InteractiveItemStack checkItem = entry.getKey();
      if (checkItem.isSimilar(item) && ((leftClick && checkItem.isLeftClick()) || (rightClick
          && checkItem.isRightClick()))) {
        return entry.getValue();
      }
    }
    return null;
  }
}
