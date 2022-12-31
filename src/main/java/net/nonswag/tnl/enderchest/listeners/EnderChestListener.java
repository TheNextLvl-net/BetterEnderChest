package net.nonswag.tnl.enderchest.listeners;

import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.inventory.InventoryEnderChest;
import net.nonswag.core.api.reflection.Reflection;
import net.nonswag.tnl.enderchest.api.EnderChest;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftInventory;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class EnderChestListener implements Listener {

    @Nonnull
    public static final List<UUID> CHESTS = new ArrayList<>();

    @EventHandler(ignoreCancelled = true)
    public void onInventoryOpen(@Nonnull InventoryOpenEvent event) {
        if (CHESTS.contains(event.getPlayer().getUniqueId())) return;
        if (!event.getInventory().getType().equals(InventoryType.ENDER_CHEST)) return;
        HumanEntity owner;
        if (((CraftInventory) event.getInventory()).getInventory() instanceof InventoryEnderChest enderchest) {
            EntityHuman human = Reflection.Field.get(enderchest, "owner");
            owner = human != null ? human.getBukkitEntity() : event.getPlayer();
        } else owner = event.getPlayer();
        event.setCancelled(true);
        if (owner instanceof Player player) {
            Reflection.Field.set(event, InventoryEvent.class, "transaction", event.getPlayer().openInventory(EnderChest.get(player)));
            CHESTS.add(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(@Nonnull InventoryClickEvent event) {
        if (!CHESTS.contains(event.getWhoClicked().getUniqueId())) return;
        if (!EnderChest.canModify(event.getWhoClicked())) event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClose(@Nonnull InventoryCloseEvent event) {
        if (!CHESTS.contains(event.getPlayer().getUniqueId())) return;
        HumanEntity owner = event.getInventory().getHolder() instanceof HumanEntity holder ? holder : event.getPlayer();
        if (EnderChest.canModify(event.getPlayer())) EnderChest.save(owner, event.getInventory());
        EntityHuman human = ((CraftHumanEntity) event.getPlayer()).getHandle();
        CHESTS.remove(event.getPlayer().getUniqueId());
        human.fO().c_(human);
    }
}
