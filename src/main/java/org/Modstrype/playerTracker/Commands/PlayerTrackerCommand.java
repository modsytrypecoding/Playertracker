package org.Modstrype.playerTracker.Commands;

import org.Modstrype.playerTracker.Util.CompassManager;
import org.Modstrype.playerTracker.Util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

public class PlayerTrackerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Kein Argument: Spieler fordert den Tracker selbst an
        if (args.length == 0) {
            if (!(sender instanceof Player p)) {
                sender.sendMessage(Messages.get("only-players"));
                return true;
            }

            if (!p.isOp()) {
                p.sendMessage(Messages.get("no-permission"));
                return true;
            }

            p.getInventory().addItem(CompassManager.createTrackerCompass());
            p.sendMessage(Messages.get("give-success"));
            return true;
        }

        // Mit Argument: Zielspieler übergeben
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null || !target.isOnline()) {
                sender.sendMessage(Messages.get("player-not-found").replace("%player%", args[0]));
                return true;
            }

            target.getInventory().addItem(CompassManager.createTrackerCompass());
            sender.sendMessage(Messages.get("give-success-other").replace("%player%", target.getName()));
            target.sendMessage(Messages.get("receive-notice"));
            return true;
        }

        sender.sendMessage(Messages.get("usage"));
        return true;
    }
}