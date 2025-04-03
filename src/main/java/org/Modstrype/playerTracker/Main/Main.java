package org.Modstrype.playerTracker.Main;

import org.Modstrype.playerTracker.Commands.PlayerTrackerCommand;
import org.Modstrype.playerTracker.Util.TrackerRunnable;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig(); // config.yml laden
        getCommand("playerTracker").setExecutor(new PlayerTrackerCommand());
        getServer().getScheduler().runTaskTimer(this, new TrackerRunnable(), 0L, 20L); // jede Sekunde
        getLogger().info("[PlayerTracker] Plugin aktiviert!");
    }

    public static Main getInstance() {
        return instance;
    }

    public boolean isTestMode() {
        return getConfig().getBoolean("testmode");
    }
}