package me.hyperburger.pickaxe3;

import me.hyperburger.pickaxe3.commands.GivePickaxeCommand;
import me.hyperburger.pickaxe3.listeners.BlockBreakListener;
import me.hyperburger.pickaxe3.utils.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public final class HyperPickaxe extends JavaPlugin {


    private static final int RESOURCE_ID = 117753;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        this.getCommand("givepickaxe").setExecutor(new GivePickaxeCommand(this));

        new UpdateChecker(this, RESOURCE_ID).checkForUpdates();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
