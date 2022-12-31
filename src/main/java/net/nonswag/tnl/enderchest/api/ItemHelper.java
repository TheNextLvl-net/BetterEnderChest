package net.nonswag.tnl.enderchest.api;

import net.nonswag.core.api.annotation.MethodsReturnNullableByDefault;
import net.nonswag.core.api.file.helper.FileHelper;
import net.nonswag.core.api.logger.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNullableByDefault
public class ItemHelper {

    public static void setContents(HumanEntity player, ItemStack[] contents) {
        try {
            File file = new File("plugins/EnderChest", player.getUniqueId() + ".yml");
            FileHelper.create(file);
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            inventory.set("items", Arrays.asList(contents));
            inventory.save(file);
        } catch (IOException e) {
            Logger.error.println(e);
        }
    }

    public static ItemStack[] getContents(HumanEntity player) {
        try {
            ItemStack[] items = new ItemStack[]{};
            File file = new File("plugins/EnderChest", player.getUniqueId() + ".yml");
            FileHelper.create(file);
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            if (!inventory.isSet("items")) return null;
            List<?> contents = inventory.getList("items");
            if (contents == null) return items;
            items = new ItemStack[contents.size()];
            for (int i = 0; i < contents.size(); i++) items[i] = (ItemStack) contents.get(i);
            return items;
        } catch (Exception e) {
            Logger.error.println(e);
            return null;
        }
    }
}
