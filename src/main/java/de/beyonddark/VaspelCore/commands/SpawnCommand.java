/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.commands;

import de.beyonddark.VaspelCore.configs.LanguageStrings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            World world = Bukkit.getServer().getWorld("world");
            if (world != null) {
                Location location = Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).getLocation();
                location.setWorld(world);
                location.setX(world.getSpawnLocation().getX());
                location.setY(world.getSpawnLocation().getY());
                location.setZ(world.getSpawnLocation().getZ());
                Objects.requireNonNull(Bukkit.getPlayer(sender.getName())).teleport(location);
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("spawn").replace("%player%", ((Player) sender).getDisplayName())));
            } else System.out.println("World Is null!");
        }
        return true;
    }
}
