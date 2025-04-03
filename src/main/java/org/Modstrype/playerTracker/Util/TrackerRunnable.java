package org.Modstrype.playerTracker.Util;

import org.Modstrype.playerTracker.Main.Main;
import org.bukkit.entity.Player;

public class TrackerRunnable implements Runnable { //update Logik für den Compass
    @Override
    public void run() {
        for (Player p : Main.getInstance().getServer().getOnlinePlayers()) { //geht alle Spieler durch die Online sind
            CompassManager.updateCompassTarget(p);
        }
    }
}