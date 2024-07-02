package me.hyperburger.pickaxe3.listeners;

import me.hyperburger.pickaxe3.HyperPickaxe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;

public class BlockBreakListener implements Listener {


    private final HyperPickaxe plugin;

    public BlockBreakListener(HyperPickaxe plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        final ItemStack playerItemInHand = player.getInventory().getItemInMainHand();
        final ItemMeta itemMeta = playerItemInHand.getItemMeta();

        if (itemMeta == null) {
            return;
        }
        final NamespacedKey widthKey = new NamespacedKey(plugin, "width");
        final NamespacedKey heightKey = new NamespacedKey(plugin, "height");
        NamespacedKey depthKey = new NamespacedKey(plugin, "depth");

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();
        if (dataContainer.has(new NamespacedKey(plugin, "width"), PersistentDataType.INTEGER) &&
                dataContainer.has(new NamespacedKey(plugin, "height"), PersistentDataType.INTEGER) &&
                dataContainer.has(new NamespacedKey(plugin, "depth"), PersistentDataType.INTEGER)) {

            Optional<Integer> width = Optional.ofNullable(dataContainer.get(widthKey, PersistentDataType.INTEGER));
            Optional<Integer> height = Optional.ofNullable(dataContainer.get(heightKey, PersistentDataType.INTEGER));
            Optional<Integer> depth = Optional.ofNullable(dataContainer.get(depthKey, PersistentDataType.INTEGER));

            // Break blocks in the specified area
            if (width.isPresent() && height.isPresent() && depth.isPresent()) {
                breakBlocksInArea(block, player, width.get(), height.get(), depth.get());
            }
        }
    }

    private void breakBlocksInArea(Block block, Player player, int width, int height, int depth) {
        int baseX = block.getX();
        int baseY = block.getY();
        int baseZ = block.getZ();

        // Calculate the starting and ending points for each dimension
        int startX = baseX - (width / 2);
        int endX = baseX + (width / 2);
        int startY = baseY - (height / 2);
        int endY = baseY + (height / 2);
        int startZ = baseZ - (depth / 2);
        int endZ = baseZ + (depth / 2);


        // Iterate over the defined area
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    Block adjacentBlock = block.getWorld().getBlockAt(x, y, z);
                    if (adjacentBlock.getType() != Material.AIR) {
                        adjacentBlock.breakNaturally(player.getInventory().getItemInMainHand());
                    }
                }
            }
        }
    }
}

