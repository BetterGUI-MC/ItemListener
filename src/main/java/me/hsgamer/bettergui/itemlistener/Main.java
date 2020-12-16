package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.itemlistener.command.Remove;
import me.hsgamer.bettergui.itemlistener.command.Set;
import me.hsgamer.bettergui.lib.core.config.path.StringConfigPath;
import me.hsgamer.bettergui.lib.simpleyaml.configuration.serialization.ConfigurationSerialization;

import static me.hsgamer.bettergui.BetterGUI.getInstance;

public final class Main extends BetterGUIAddon {

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

        ITEM_REQUIRED.setConfig(getInstance().getMessageConfig());
        getInstance().getMessageConfig().saveConfig();

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
