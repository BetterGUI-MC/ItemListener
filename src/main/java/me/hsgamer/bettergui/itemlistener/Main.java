package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.itemlistener.command.Remove;
import me.hsgamer.bettergui.itemlistener.command.Set;
import me.hsgamer.bettergui.object.addon.Addon;
import me.hsgamer.bettergui.util.config.path.StringConfigPath;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

public final class Main extends Addon {

  public static final StringConfigPath ITEM_REQUIRED = new StringConfigPath("item-required",
      "&cYou should have an item in your hand");
  private static ItemStorage storage;

  public static ItemStorage getStorage() {
    return storage;
  }

  @Override
  public boolean onLoad() {
    ConfigurationSerialization.registerClass(InteractiveItemStack.class);
    setupConfig();
    registerListener(new ItemListener());

    ITEM_REQUIRED.setConfig(getPlugin().getMessageConfig());
    getPlugin().getMessageConfig().saveConfig();

    return true;
  }

  @Override
  public void onEnable() {
    storage = new ItemStorage(this);
    registerCommand(new Set());
    registerCommand(new Remove());
  }

  @Override
  public void onDisable() {
    storage.save();
  }

  @Override
  public void onReload() {
    storage.save();
    reloadConfig();
    storage.load();
  }
}
