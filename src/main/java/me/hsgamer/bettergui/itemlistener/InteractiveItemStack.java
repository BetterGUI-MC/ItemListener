package me.hsgamer.bettergui.itemlistener;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class InteractiveItemStack implements ConfigurationSerializable {

  private final ItemStack itemStack;
  private boolean leftClick = true;
  private boolean rightClick = true;

  public InteractiveItemStack(ItemStack stack) {
    this.itemStack = stack;
  }

  public static InteractiveItemStack deserialize(Map<String, Object> args) {
    InteractiveItemStack itemStack = new InteractiveItemStack(ItemStack.deserialize(args));
    itemStack.leftClick = (boolean) args.get("left");
    itemStack.rightClick = (boolean) args.get("right");
    return itemStack;
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = itemStack.serialize();
    map.put("left", leftClick);
    map.put("right", rightClick);
    return map;
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
}
