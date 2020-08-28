package de.beyonddark.VaspelCore.commands.tpa;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPDenyCommand implements CommandExecutor {
    private final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (p != null) {
            if (plugin.currentRequest.containsKey(p.getName())) {
                Player rejectedPlayer = plugin.getServer().getPlayer(plugin.currentRequest.get(p.getName()));
                plugin.currentRequest.remove(p.getName());
                if (rejectedPlayer != null) {
                    rejectedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("tpa-rejected").replace("%player%", p.getDisplayName())));
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("tpa-request-rejected").replace("%player%", rejectedPlayer.getDisplayName())));
                    return true;
                }
            } else {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("no-requests")));
                return false;
            }
        } else {
            assert sender != null;
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("only-player")));
            return false;
        }
        return true;
    }
}
