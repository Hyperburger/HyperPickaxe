package me.hyperburger.pickaxe3;

import me.hyperburger.pickaxe3.commands.GivePickaxeCommand;
import me.hyperburger.pickaxe3.listeners.BlockBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class HyperPickaxe extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getCommand("givepickaxe").setExecutor(new GivePickaxeCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
