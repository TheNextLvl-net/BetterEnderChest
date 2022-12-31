package net.nonswag.tnl.enderchest.api;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class EnderChest {

    @Nonnull
    public static String TITLE = "Ender Chest (%s)";

    @Nonnull
    public static Inventory get(@Nonnull HumanEntity player) {
        Inventory inventory = Bukkit.createInventory(player, getRows(player) * 9, Component.text(getTitle(player)));
        ItemStack[] contents = ItemHelper.getContents(player);
        if (contents == null) contents = player.getEnderChest().getContents();
        for (int slot = 0; slot < contents.length && slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, contents[slot]);
        }
        return inventory;
    }

    public static void save(@Nonnull HumanEntity player, @Nonnull Inventory inventory) {
        ItemHelper.setContents(player, inventory.getContents());
    }

    @Nonnull
    public static String getTitle(@Nonnull HumanEntity player) {
        return TITLE.formatted(getRows(player));
    }

    public static int getRows(@Nonnull HumanEntity player) {
        if (player.hasPermission("enderchest.rows.6")) return 6;
        if (player.hasPermission("enderchest.rows.5")) return 5;
        if (player.hasPermission("enderchest.rows.4")) return 4;
        if (player.hasPermission("enderchest.rows.3")) return 3;
        if (player.hasPermission("enderchest.rows.2")) return 2;
        return 1;
    }

    public static boolean canModify(@Nonnull HumanEntity player) {
        return player.hasPermission("enderchest.modify");
    }
}
