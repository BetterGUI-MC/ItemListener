package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.api.addon.GetPlugin;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;
import me.hsgamer.hscore.logger.common.LogLevel;
import me.hsgamer.hscore.logger.common.Logger;
import me.hsgamer.hscore.logger.provider.LoggerProvider;
import org.bukkit.Bukkit;

import java.io.File;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main implements Expansion, GetPlugin, DataFolder {
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final ItemStorage storage = new ItemStorage(this);

    @Override
    public boolean onLoad() {
        Logger logger = LoggerProvider.getLogger("ItemListener");
        if (!Bukkit.getPluginManager().isPluginEnabled("CommandItems")) {
            logger.log(LogLevel.ERROR, "The addon is deprecated and will be removed in the future");
            logger.log(LogLevel.ERROR, "Please use CommandItems instead");
            logger.log(LogLevel.ERROR, "https://www.spigotmc.org/resources/106847/");
            logger.log(LogLevel.ERROR, "Install it and use /convertitemmenu to convert the items");
            return false;
        } else {
            logger.log(LogLevel.WARN, "The addon is deprecated and will be removed in the future");
            logger.log(LogLevel.WARN, "Please use CommandItems instead");
            logger.log(LogLevel.WARN, "Use /convertitemmenu to convert the items");
            return true;
        }
    }

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
