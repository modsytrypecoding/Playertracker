package org.Modstrype.playerTracker.Util;

import org.Modstrype.playerTracker.Main.Main;
import org.bukkit.ChatColor;

public class Messages { //Message Handler für die Config
    public static String get(String key) {
        String raw = Main.getInstance().getConfig().getString("messages." + key, "&c[Fehlende Nachricht: " + key + "]");
        return ChatColor.translateAlternateColorCodes('&', raw);
    }
}