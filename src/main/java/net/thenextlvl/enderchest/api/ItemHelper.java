package net.thenextlvl.enderchest.api;

import core.annotation.MethodsReturnNullableByDefault;
import core.annotation.ParametersAreNonnullByDefault;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemStack;

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
            createFile(file);
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            inventory.set("items", Arrays.asList(contents));
            inventory.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ItemStack[] getContents(HumanEntity player) {
        try {
            ItemStack[] items = new ItemStack[]{};
            File file = new File("plugins/EnderChest", player.getUniqueId() + ".yml");
            createFile(file);
            YamlConfiguration inventory = YamlConfiguration.loadConfiguration(file);
            if (!inventory.isSet("items")) return null;
            List<?> contents = inventory.getList("items");
            if (contents == null) return items;
            items = new ItemStack[contents.size()];
            for (int i = 0; i < contents.size(); i++) items[i] = (ItemStack) contents.get(i);
            return items;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void createFile(File file) throws IOException {
        File absoluteFile = file.getAbsoluteFile();
        absoluteFile.getParentFile().mkdirs();
        absoluteFile.createNewFile();
    }
}
