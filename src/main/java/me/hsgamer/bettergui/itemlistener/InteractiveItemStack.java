package me.hsgamer.bettergui.itemlistener;

import java.util.Map;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;

public class InteractiveItemStack extends ItemStack implements ConfigurationSerializable {

  private boolean leftClick = true;
  private boolean rightClick = true;

  public InteractiveItemStack(ItemStack stack) {
    super(stack);
  }

  public static InteractiveItemStack deserialize(Map<String, Object> args) {
    InteractiveItemStack itemStack = new InteractiveItemStack(ItemStack.deserialize(args));
    itemStack.leftClick = (boolean) args.get("left");
    itemStack.rightClick = (boolean) args.get("right");
    return itemStack;
  }

  @Override
  public Map<String, Object> serialize() {
    Map<String, Object> map = super.serialize();
    map.put("left", leftClick);
    map.put("right", rightClick);
    return map;
  }

  @Override
  public boolean equals(Object object) {
    if (object instanceof ItemStack) {
      if (object instanceof InteractiveItemStack) {
        return super.equals(object)
            && this.leftClick == ((InteractiveItemStack) object).leftClick
            && this.rightClick == ((InteractiveItemStack) object).rightClick;
      }
      return super.equals(object);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
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
