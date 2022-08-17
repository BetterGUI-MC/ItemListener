package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.hscore.config.Config;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ItemStorage {

    private final Map<InteractiveItemStack, String> itemToMenuMap = new HashMap<>();
    private final Main main;


    public ItemStorage(Main main) {
        this.main = main;
    }

    @SuppressWarnings("unchecked")
    public void load() {
        itemToMenuMap.clear();
        Config config = main.getConfig();
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
        main.getConfig().getKeys(false).forEach(main.getConfig()::remove);

        map.forEach((s, list) -> main.getConfig().set(s, list));
        main.getConfig().save();
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
