package net.thenextlvl.enderchest.listener;

import core.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.thenextlvl.enderchest.api.EnderChest;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R3.inventory.CraftInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.InventoryView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class EnderChestListener implements Listener {
    public static final List<UUID> CHESTS = new ArrayList<>();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) throws NoSuchFieldException, IllegalAccessException {
        Player player = (Player) event.getPlayer();
        if (CHESTS.contains(player.getUniqueId())) return;
        if (!event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) return;
        Player owner;
        if (((CraftInventory) event.getInventory()).getInventory() instanceof PlayerEnderChestContainer enderchest) {
            owner = enderchest.getBukkitOwner() instanceof Player p ? p : player;
        } else owner = player;
        event.setCancelled(true);
        InventoryView view = player.openInventory(EnderChest.get(owner));
        Field transaction = InventoryEvent.class.getDeclaredField("transaction");
        transaction.trySetAccessible();
        transaction.set(event, view);
        CHESTS.add(player.getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (!CHESTS.contains(player.getUniqueId())) return;
        if (!EnderChest.canModify(player)) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        if (!CHESTS.contains(player.getUniqueId())) return;
        Player owner = event.getInventory().getHolder() instanceof Player holder ? holder : player;
        if (EnderChest.canModify(player)) EnderChest.save(owner, event.getInventory());
        var human = ((CraftHumanEntity) player).getHandle();
        CHESTS.remove(player.getUniqueId());
        human.getEnderChestInventory().stopOpen(human);
    }
}
