/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.commands.home;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;
import de.beyonddark.VaspelCore.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public class HomeCommand implements CommandExecutor {

    private final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            plugin.getLogger().log(Level.WARNING, ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("only-player"))));
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("missing-name-home"))));
                return false;
            }
            try {
                if (Utils.homeIsNull(player, args[0].toLowerCase())) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("must-sethome"))));
                } else if (MainConfig.get().getBoolean("home-command-delay")) {
                    int coolDown = MainConfig.get().getInt("home-time-delay");
                    if (coolDown == 0) {
                        plugin.sendPlayerHome(player, args[0].toLowerCase());
                        return false;
                    }
                    if (coolDown > 0) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("teleport-soon").replace("%seconds%", "" + coolDown).replace("%name%", args[0].toLowerCase()))));
                        BukkitScheduler scheduler = plugin.getServer().getScheduler();
                        scheduler.scheduleSyncDelayedTask(plugin, () -> {
                            try {
                                plugin.sendPlayerHome(player, args[0].toLowerCase());
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        }, 20L * coolDown);
                    }
                } else {
                    plugin.sendPlayerHome(player, args[0].toLowerCase());
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return true;
    }
}
