package net.nonswag.tnl.enderchest.listeners;

import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.enderchest.api.EnderChest;
import org.bukkit.craftbukkit.v1_19_R2.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R2.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.InventoryView;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@FieldsAreNonnullByDefault
@ParametersAreNonnullByDefault
public class EnderChestListener implements Listener {
    public static final List<UUID> CHESTS = new ArrayList<>();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (CHESTS.contains(event.getPlayer().getUniqueId())) return;
        if (!event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) return;
        HumanEntity owner;
        if (((CraftInventory) event.getInventory()).getInventory() instanceof PlayerEnderChestContainer enderchest) {
            owner = enderchest.getBukkitOwner() instanceof HumanEntity human ? human : event.getPlayer();
        } else owner = event.getPlayer();
        event.setCancelled(true);
        InventoryView view = event.getPlayer().openInventory(EnderChest.get(owner));
        Reflection.Field.setByType(event, InventoryEvent.class, InventoryView.class, view);
        CHESTS.add(event.getPlayer().getUniqueId());
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!CHESTS.contains(event.getWhoClicked().getUniqueId())) return;
        if (!EnderChest.canModify(event.getWhoClicked())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!CHESTS.contains(event.getPlayer().getUniqueId())) return;
        HumanEntity owner = event.getInventory().getHolder() instanceof HumanEntity holder ? holder : event.getPlayer();
        if (EnderChest.canModify(event.getPlayer())) EnderChest.save(owner, event.getInventory());
        var human = ((CraftHumanEntity) event.getPlayer()).getHandle();
        CHESTS.remove(event.getPlayer().getUniqueId());
        human.getEnderChestInventory().stopOpen(human);
    }
}
