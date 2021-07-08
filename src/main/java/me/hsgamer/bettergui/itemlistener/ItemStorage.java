package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.lib.core.config.Config;
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
        Config config = addon.getConfig();
        for (String s : config.getKeys(false)) {
            Optional.ofNullable(config.getInstance(s, List.class))
                    .ifPresent(list -> list.forEach(o -> {
                        if (o instanceof Map) {
                            itemToMenuMap.put(InteractiveItemStack.deserialize((Map<String, Object>) o), s + ".yml");
                        }
                    }));
        }
    }

    public void save() {
        Map<String, List<Map<String, Object>>> map = new HashMap<>();
        itemToMenuMap.forEach((item, s) -> {
            s = s.replace(".yml", "");
            map.computeIfAbsent(s, s1 -> new ArrayList<>()).add(item.serialize());
        });

        // Clear old config
        addon.getConfig().getKeys(false).forEach(addon.getConfig()::remove);

        map.forEach((s, list) -> addon.getConfig().set(s, list));
        addon.saveConfig();
    }

    public void set(InteractiveItemStack item, String menu) {
        itemToMenuMap.put(item, menu);
    }

    public void remove(String menu) {
        itemToMenuMap.entrySet().removeIf(entry -> entry.getValue().equals(menu));
    }

    public Optional<Map.Entry<InteractiveItemStack, String>> getMenu(ItemStack item, boolean leftClick, boolean rightClick) {
        return itemToMenuMap.entrySet().stream().filter(entry -> {
            InteractiveItemStack checkItem = entry.getKey();
            return checkItem.getItemStack().isSimilar(item) && ((leftClick && checkItem.isLeftClick())
                    || (rightClick
                    && checkItem.isRightClick()));
        }).findFirst();
    }
}
