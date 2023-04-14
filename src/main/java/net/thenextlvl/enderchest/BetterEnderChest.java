package net.thenextlvl.enderchest;

import core.api.placeholder.Placeholder;
import lombok.Getter;
import lombok.experimental.Accessors;
import net.thenextlvl.enderchest.listener.EnderChestListener;
import net.thenextlvl.enderchest.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class BetterEnderChest extends JavaPlugin {
    @Accessors(fluent = true)
    private final Placeholder.Formatter<Player> formatter = new Placeholder.Formatter<>();

    @Override
    public void onLoad() {
        Messages.init();
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EnderChestListener(), this);
    }
}
