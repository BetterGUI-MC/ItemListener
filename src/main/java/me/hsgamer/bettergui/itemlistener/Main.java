package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.api.addon.GetPlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main implements Expansion, GetPlugin, DataFolder {
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final ItemStorage storage = new ItemStorage(this);

    @Override
    public void onEnable() {
        storage.load();

        getInstance().registerCommand(new ConvertItemCommand(this));
    }

    @Override
    public void onDisable() {
        storage.save();
    }

    public ItemStorage getStorage() {
        return storage;
    }

    public Config getConfig() {
        return config;
    }
}
