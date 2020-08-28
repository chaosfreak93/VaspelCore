/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.commands.home;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public class SetHomeCommand implements CommandExecutor {

    private final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            plugin.getLogger().log(Level.WARNING, ChatColor.translateAlternateColorCodes('&', (Objects.requireNonNull(LanguageStrings.get().getString("only-player")))));
        } else if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("missing-name-sethome"))));
                return false;
            }
            try {
                plugin.setPlayerHome(player, args[0].toLowerCase());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
