package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemStorage {

    private final Map<InteractiveItemStack, String> itemToMenuMap = new HashMap<>();
    private final BetterGUIAddon addon;

    public ItemStorage(BetterGUIAddon addon) {
        this.addon = addon;
        load();
    }

    @SuppressWarnings("unchecked")
    public void load() {
        itemToMenuMap.clear();
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
