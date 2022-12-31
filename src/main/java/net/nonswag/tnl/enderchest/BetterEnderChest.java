package net.nonswag.tnl.enderchest;

import net.nonswag.tnl.enderchest.listeners.EnderChestListener_16;
import net.nonswag.tnl.enderchest.listeners.EnderChestListener;
import net.nonswag.tnl.enderchest.listeners.EnderChestListener_Base;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;
import net.nonswag.tnl.listener.api.version.Version;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BetterEnderChest extends TNLPlugin {

    @Nullable
    private static BetterEnderChest instance = null;

    @Override
    public void enable() {
        instance = this;
        getEventManager().registerListener(new EnderChestListener_Base());
        getEventManager().registerListener(new EnderChestListener());
    }

    @Nonnull
    public static BetterEnderChest getInstance() {
        assert instance != null;
        return instance;
    }
}
