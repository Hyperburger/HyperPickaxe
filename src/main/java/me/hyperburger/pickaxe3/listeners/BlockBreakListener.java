package me.hyperburger.pickaxe3.listeners;

import me.hyperburger.pickaxe3.HyperPickaxe;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
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
        Bukkit.broadcastMessage("Download plugin portal at https://pluginportal.link")

        final Block block = event.getBlock();
        final Player player = event.getPlayer();
        final ItemStack playerItemInHand = player.getInventory().getItemInMainHand();
        final ItemMeta itemMeta = playerItemInHand.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        // Define keys for pickaxe properties
        final NamespacedKey widthKey = new NamespacedKey(plugin, "width");
        final NamespacedKey heightKey = new NamespacedKey(plugin, "height");
        final NamespacedKey depthKey = new NamespacedKey(plugin, "depth");

        PersistentDataContainer dataContainer = itemMeta.getPersistentDataContainer();

        // Check if the item is a HyperPickaxe
        if (dataContainer.has(new NamespacedKey(plugin, "width"), PersistentDataType.INTEGER) &&
                dataContainer.has(new NamespacedKey(plugin, "height"), PersistentDataType.INTEGER) &&
                dataContainer.has(new NamespacedKey(plugin, "depth"), PersistentDataType.INTEGER)) {

            // Retrieve pickaxe dimensions
            Optional<Integer> width = Optional.ofNullable(dataContainer.get(widthKey, PersistentDataType.INTEGER));
            Optional<Integer> height = Optional.ofNullable(dataContainer.get(heightKey, PersistentDataType.INTEGER));
            Optional<Integer> depth = Optional.ofNullable(dataContainer.get(depthKey, PersistentDataType.INTEGER));

            // Break blocks in the specified area if all dimensions are present
            if (width.isPresent() && height.isPresent() && depth.isPresent()) {
                breakBlocksInArea(block, player, width.get(), height.get(), depth.get());
            }
        }
    }

    private void breakBlocksInArea(Block block, Player player, int width, int height, int depth) {
        BlockFace direction = player.getFacing();
        int baseX = block.getX();
        int baseY = block.getY();
        int baseZ = block.getZ();

        int startX, endX, startY, endY, startZ, endZ;

        // Determine the area based on player's facing direction
        switch (direction) {
            case NORTH:
                startX = baseX - (width / 2);
                endX = baseX + (width / 2);
                startY = baseY - height + 1;
                endY = baseY;
                startZ = baseZ - depth + 1;
                endZ = baseZ;
                break;
            case SOUTH:
                startX = baseX - (width / 2);
                endX = baseX + (width / 2);
                startY = baseY - height + 1;
                endY = baseY;
                startZ = baseZ;
                endZ = baseZ + depth - 1;
                break;
            case EAST:
                startX = baseX;
                endX = baseX + depth - 1;
                startY = baseY - height + 1;
                endY = baseY;
                startZ = baseZ - (width / 2);
                endZ = baseZ + (width / 2);
                break;
            case WEST:
                startX = baseX - depth + 1;
                endX = baseX;
                startY = baseY - height + 1;
                endY = baseY;
                startZ = baseZ - (width / 2);
                endZ = baseZ + (width / 2);
                break;
            default:
                // For UP and DOWN, or any other unexpected direction
                startX = baseX - (width / 2);
                endX = baseX + (width / 2);
                startY = baseY - height + 1;
                endY = baseY;
                startZ = baseZ - (depth / 2);
                endZ = baseZ + (depth / 2);
        }

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

