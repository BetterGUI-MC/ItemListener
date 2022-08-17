package me.hsgamer.bettergui.itemlistener;

import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.annotated.AnnotatedConfig;
import me.hsgamer.hscore.config.annotation.ConfigPath;

public class ExtraMessageConfig extends AnnotatedConfig {
    public final @ConfigPath("item-required") String itemRequired;

    public ExtraMessageConfig(Config config) {
        super(config);
        itemRequired = "&cYou should have an item in your hand";
    }
}
