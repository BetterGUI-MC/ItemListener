package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.lib.simpleyaml.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class InteractiveItemStack implements ConfigurationSerializable {

    private final ItemStack itemStack;
    private final List<String> args = new ArrayList<>();
    private boolean leftClick = true;
    private boolean rightClick = true;

    public InteractiveItemStack(ItemStack stack) {
        this.itemStack = stack;
    }

    @SuppressWarnings("unchecked")
    public static InteractiveItemStack deserialize(Map<String, Object> map) {
        InteractiveItemStack itemStack = new InteractiveItemStack(ItemStack.deserialize(map));
        itemStack.leftClick = (boolean) map.get("left");
        itemStack.rightClick = (boolean) map.get("right");
        if (map.containsKey("args")) {
            itemStack.args.addAll((Collection<? extends String>) map.get("args"));
        }
        return itemStack;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> input) {
        this.args.addAll(input);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public void setLeftClick(boolean leftClick) {
        this.leftClick = leftClick;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = itemStack.serialize();
        map.put("left", leftClick);
        map.put("right", rightClick);
        if (!args.isEmpty()) {
            map.put("args", args);
        }
        return map;
    }
}
