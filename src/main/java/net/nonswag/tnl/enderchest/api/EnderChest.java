package net.nonswag.tnl.enderchest.api;

import net.kyori.adventure.text.Component;
import net.nonswag.core.api.annotation.MethodsReturnNonnullByDefault;
import net.nonswag.core.api.message.Message;
import net.nonswag.core.api.message.Placeholder;
import net.nonswag.tnl.enderchest.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EnderChest {

    public static Inventory get(HumanEntity player) {
        Inventory inventory = Bukkit.createInventory(player, getRows(player) * 9, Component.text(getTitle(player)));
        ItemStack[] contents = ItemHelper.getContents(player);
        if (contents == null) contents = player.getEnderChest().getContents();
        for (int slot = 0; slot < contents.length && slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, contents[slot]);
        }
        return inventory;
    }

    public static void save(HumanEntity player, Inventory inventory) {
        ItemHelper.setContents(player, inventory.getContents());
    }

    public static String getTitle(HumanEntity player) {
        return Message.format(Messages.ENDER_CHEST_TITLE.message(), new Placeholder("rows", getRows(player)));
    }

    public static int getRows(HumanEntity player) {
        if (player.hasPermission("enderchest.rows.6")) return 6;
        if (player.hasPermission("enderchest.rows.5")) return 5;
        if (player.hasPermission("enderchest.rows.4")) return 4;
        if (player.hasPermission("enderchest.rows.3")) return 3;
        if (player.hasPermission("enderchest.rows.2")) return 2;
        return 1;
    }

    public static boolean canModify(HumanEntity player) {
        return player.hasPermission("enderchest.modify");
    }
}
