package me.hyperburger.pickaxe3.commands;

import me.hyperburger.pickaxe3.HyperPickaxe;
import me.hyperburger.pickaxe3.utils.Color;
import me.hyperburger.pickaxe3.utils.PickaxeUtils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.hyperburger.pickaxe3.HyperPickaxe;
import me.hyperburger.pickaxe3.utils.Color;
import me.hyperburger.pickaxe3.utils.PickaxeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GivePickaxeCommand implements CommandExecutor {

    private final HyperPickaxe plugin;

    public GivePickaxeCommand(HyperPickaxe plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length != 6) {
            sender.sendMessage("Console usage: /givepickaxe <material> <displayName> <width> <height> <depth> <player>");
            return true;
        }

        if (args.length < 5 || args.length > 6) {
            sender.sendMessage("Usage: /givepickaxe <material> <displayName> <width> <height> <depth> [player]");
            return true;
        }

        String materialName = args[0];
        String displayName = args[1];
        int width, height, depth;
        Player targetPlayer;

        Material material = Material.getMaterial(materialName.toUpperCase());
        if (material == null || !material.toString().contains("PICKAXE")) {
            sender.sendMessage("Invalid material. Please enter a valid pickaxe material.");
            return true;
        }

        try {
            width = Integer.parseInt(args[2]);
            height = Integer.parseInt(args[3]);
            depth = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Width, height, and depth must be integers.");
            return true;
        }

        if (args.length == 6) {
            targetPlayer = Bukkit.getPlayer(args[5]);
            if (targetPlayer == null) {
                sender.sendMessage("Player not found: " + args[5]);
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Console must specify a player name.");
                return true;
            }
            targetPlayer = (Player) sender;
        }

        PickaxeUtils.givePickaxe(plugin, targetPlayer, material, Color.c(displayName), width, height, depth);
        sender.sendMessage("Pickaxe given to " + targetPlayer.getName() + " with width: " + width + ", height: " + height + ", depth: " + depth);
        if (sender != targetPlayer) {
            targetPlayer.sendMessage("You received a pickaxe from " + sender.getName() + " with width: " + width + ", height: " + height + ", depth: " + depth);
        }
        return true;
    }
}