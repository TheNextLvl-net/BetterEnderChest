package net.nonswag.tnl.enderchest;

import net.nonswag.tnl.enderchest.listeners.EnderChestListener;
import net.nonswag.tnl.enderchest.utils.Messages;
import net.nonswag.tnl.listener.api.plugin.TNLPlugin;

public class BetterEnderChest extends TNLPlugin {

    @Override
    public void load() {
        Messages.init();
    }

    @Override
    public void enable() {
        getEventManager().registerListener(new EnderChestListener());
    }
}
