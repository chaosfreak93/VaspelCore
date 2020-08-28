package de.beyonddark.VaspelCore.commands.admin;

import de.beyonddark.VaspelCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (p.hasPermission("vaspelcore.vanish") || p.isOp()) {
            if (Main.vanishedPlayer.contains(p)) {
                p.sendMessage("Du bist wieder da!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    all.showPlayer(plugin, p);
                }
                p.setFlying(false);
                p.setAllowFlight(false);
                Main.vanishedPlayer.remove(p);
            } else {

                p.sendMessage("Du bist weg!");
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (!(all.hasPermission("vaspelcore.vanish.see")) || !(all.isOp())) {
                        all.hidePlayer(plugin, p);
                    }
                }
                p.setAllowFlight(true);
                p.setFlying(true);
                Main.vanishedPlayer.add(p);
            }
        }
        return true;
    }
}
