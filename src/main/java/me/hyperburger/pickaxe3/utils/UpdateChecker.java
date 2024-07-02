package me.hyperburger.pickaxe3.utils;

import me.hyperburger.pickaxe3.HyperPickaxe;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

import org.bukkit.ChatColor;

public class UpdateChecker {

    private final HyperPickaxe plugin;
    private final int resourceId;

    public UpdateChecker(HyperPickaxe plugin, int resourceId) {
        this.plugin = plugin;
        this.resourceId = resourceId;
    }

    public void checkForUpdates() {
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId).openStream();
                 Scanner scanner = new Scanner(inputStream)) {
                if (scanner.hasNext()) {
                    String latestVersion = scanner.next();
                    String currentVersion = this.plugin.getDescription().getVersion();
                    if (!currentVersion.equalsIgnoreCase(latestVersion)) {
                        plugin.getLogger().info("==================================");
                        plugin.getLogger().info("There is a new update available for HyperPickaxe!");
                        plugin.getLogger().info("Current version: " + currentVersion);
                        plugin.getLogger().info("Latest version: " + latestVersion);
                        plugin.getLogger().info("Download the latest version from:");
                        plugin.getLogger().info("https://www.spigotmc.org/resources/" + this.resourceId);
                        plugin.getLogger().info("==================================");
                    } else {
                        plugin.getLogger().info("=============================");
                        plugin.getLogger().info( "    HyperPickaxe is up to date!");
                        plugin.getLogger().info( "    Current version: " + currentVersion);
                        plugin.getLogger().info( "    Dig on with confidence!");
                        plugin.getLogger().info( "=============================");
                    }
                }
            } catch (IOException exception) {
                plugin.getLogger().warning("Unable to check for updates: " + exception.getMessage());
            }
        });
    }
}