package me.hsgamer.bettergui.itemlistener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.inventory.ItemStack;

public class ItemStorage {

  private final Map<InteractiveItemStack, String> itemToMenuMap = new HashMap<>();
  private final Addon addon;

  public ItemStorage(Addon addon) {
    this.addon = addon;
    load();
  }

  @SuppressWarnings("unchecked")
  public void load() {
    addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().getMapList(s)
        .forEach(map -> itemToMenuMap
            .put(InteractiveItemStack.deserialize((Map<String, Object>) map), s + ".yml")));
  }

  public void save() {
    Map<String, List<Map<String, Object>>> map = new HashMap<>();
    itemToMenuMap.forEach((item, s) -> {
      s = s.replace(".yml", "");
      if (!map.containsKey(s)) {
        map.put(s, new ArrayList<>());
      }
      map.get(s).add(item.serialize());
    });

    // Clear old config
    addon.getConfig().getKeys(false).forEach(s -> addon.getConfig().set(s, null));

    map.forEach((s, list) -> addon.getConfig().set(s, list));
    addon.saveConfig();
  }

  public void set(InteractiveItemStack item, String menu) {
    itemToMenuMap.put(item, menu);
  }

  public void remove(String menu) {
    itemToMenuMap.entrySet().removeIf(entry -> entry.getValue().equals(menu));
  }

  public Optional<Map.Entry<InteractiveItemStack, String>> getMenu(ItemStack item,
      boolean leftClick, boolean rightClick) {
    return itemToMenuMap.entrySet().stream().filter(entry -> {
      InteractiveItemStack checkItem = entry.getKey();
      return checkItem.getItemStack().isSimilar(item) && ((leftClick && checkItem.isLeftClick())
          || (rightClick
          && checkItem.isRightClick()));
    }).findFirst();
  }
}
