/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.commands.admin;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    public Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("vaspelcore.reload") || sender.isOp()) {
            MainConfig.reload();
            LanguageStrings.reload();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("reload")));
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("no-permission")));
        }
        return true;
    }
}
