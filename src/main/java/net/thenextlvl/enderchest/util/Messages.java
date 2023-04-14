package net.thenextlvl.enderchest.util;

import core.annotation.FieldsAreNonnullByDefault;
import core.api.file.format.MessageFile;
import core.api.placeholder.MessageKey;
import net.thenextlvl.enderchest.BetterEnderChest;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

@FieldsAreNonnullByDefault
public class Messages {
    private static final BetterEnderChest plugin = JavaPlugin.getPlugin(BetterEnderChest.class);
    public static final MessageKey<Player> ENDER_CHEST_TITLE = new MessageKey<>("ender-chest-title", plugin.formatter()).register();

    public static void init() {
        initEnglish();
        initGerman();
    }

    private static void initEnglish() {
        var file = MessageFile.getOrCreate(Locale.forLanguageTag("en-US"));
        file.setDefault(ENDER_CHEST_TITLE, "Ender Chest (%rows%)");
        file.save();
    }

    private static void initGerman() {
        var file = MessageFile.getOrCreate(Locale.forLanguageTag("de-DE"));
        file.setDefault(ENDER_CHEST_TITLE, "Endertruhe (%rows%)");
        file.save();
    }
}
