package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.object.addon.Addon;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public final class Main extends Addon {

  private static ItemStorage storage;

  public static ItemStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    ConfigurationSerialization.registerClass(InteractiveItemStack.class);
    setupConfig();
    return true;
  }

  @Override
  public void onEnable() {
    storage = new ItemStorage(this);
  }

  @Override
  public void onDisable() {
    storage.save();
  }
}
