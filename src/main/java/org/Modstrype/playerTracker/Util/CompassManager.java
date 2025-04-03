package org.Modstrype.playerTracker.Util;

import org.Modstrype.playerTracker.Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.enchantments.Enchantment;

import java.util.*;

public class CompassManager {

    private static final Map<UUID, Location> lastTargetLocations = new HashMap<>();

    public static ItemStack createTrackerCompass() {
        ItemStack compass = new ItemStack(Material.COMPASS);
        ItemMeta meta = compass.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.BLUE + "Player Tracker");
            meta.addEnchant(Enchantment.UNBREAKING, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            compass.setItemMeta(meta);
        }

        return compass;
    }

    public static void updateCompassTarget(Player player) {
        ItemStack compass = getHeldTrackerCompass(player);
        if (compass == null) return;

        boolean testmode = Main.getInstance().isTestMode();

        if (testmode) {
            Location village = findNearestVillage(player);
            if (village != null) {
                player.setCompassTarget(village);
            } else {
                player.sendMessage(Messages.get("no-village-found"));
            }
            return;
        }

        Player nearest = getNearestPlayer(player);
        if (nearest == null || nearest.equals(player)) return;

        Location targetLoc = nearest.getLocation();


        if (player.getWorld().getEnvironment() != World.Environment.NORMAL) return;

        player.setCompassTarget(targetLoc);
    }

    private static Player getNearestPlayer(Player source) {
        double closestDistance = Double.MAX_VALUE;
        Player closest = null;

        for (Player other : Bukkit.getOnlinePlayers()) {
            if (other.equals(source)) continue;
            if (!other.getWorld().equals(source.getWorld())) continue;

            double dist = source.getLocation().distanceSquared(other.getLocation());
            if (dist < closestDistance) {
                closestDistance = dist;
                closest = other;
            }
        }

        return closest;
    }

    private static ItemStack getHeldTrackerCompass(Player p) {
        ItemStack main = p.getInventory().getItemInMainHand();
        if (isTracker(main)) return main;

        ItemStack off = p.getInventory().getItemInOffHand();
        if (isTracker(off)) return off;

        return null;
    }

    public static boolean isTracker(ItemStack item) {
        if (item == null || item.getType() != Material.COMPASS) return false;
        if (!item.hasItemMeta()) return false;

        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasDisplayName()
                && meta.getDisplayName().contains("Player Tracker");
    }

    private static Location findNearestVillage(Player player) {
        try {
            World world = player.getWorld();
            if (!world.getEnvironment().equals(World.Environment.NORMAL)) return null;

            return world.locateNearestStructure(player.getLocation(), StructureType.VILLAGE, 500, false);
        } catch (Exception e) {
            return null;
        }
    }

    public static void clearTrackingCache(UUID playerUUID) {
        lastTargetLocations.remove(playerUUID);
    }
}