package net.thenextlvl.enderchest.api;

import core.annotation.MethodsReturnNonnullByDefault;
import core.annotation.ParametersAreNonnullByDefault;
import core.api.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import net.thenextlvl.enderchest.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EnderChest {

    public static Inventory get(Player player) {
        Inventory inventory = Bukkit.createInventory(player, getRows(player) * 9, Component.text(getTitle(player)));
        ItemStack[] contents = ItemHelper.getContents(player);
        if (contents == null) contents = player.getEnderChest().getContents();
        for (int slot = 0; slot < contents.length && slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, contents[slot]);
        }
        return inventory;
    }

    public static void save(Player player, Inventory inventory) {
        ItemHelper.setContents(player, inventory.getContents());
    }

    public static String getTitle(Player player) {
        return Messages.ENDER_CHEST_TITLE.message(player.locale(), Placeholder.of("rows", getRows(player)));
    }

    public static int getRows(Player player) {
        if (player.hasPermission("enderchest.rows.6")) return 6;
        if (player.hasPermission("enderchest.rows.5")) return 5;
        if (player.hasPermission("enderchest.rows.4")) return 4;
        if (player.hasPermission("enderchest.rows.3")) return 3;
        if (player.hasPermission("enderchest.rows.2")) return 2;
        return 1;
    }

    public static boolean canModify(Player player) {
        return player.hasPermission("enderchest.modify");
    }
}
