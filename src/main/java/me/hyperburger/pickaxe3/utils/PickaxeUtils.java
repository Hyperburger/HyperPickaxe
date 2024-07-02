package me.hyperburger.pickaxe3.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class PickaxeUtils {

    public static void givePickaxe(JavaPlugin plugin, Player player, Material material, String displayName, int width, int height, int depth) {
        final ItemStack pickaxe = new ItemStack(material);
        final ItemMeta itemMeta = pickaxe.getItemMeta();

        if (itemMeta == null) return;

        itemMeta.setDisplayName(displayName);
        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        dataContainer.set(new NamespacedKey(plugin, "width"), PersistentDataType.INTEGER, width);
        dataContainer.set(new NamespacedKey(plugin, "height"), PersistentDataType.INTEGER, height);
        dataContainer.set(new NamespacedKey(plugin, "depth"), PersistentDataType.INTEGER, depth);

        pickaxe.setItemMeta(itemMeta);
        player.getInventory().addItem(pickaxe);
    }
}
