package de.beyonddark.VaspelCore.commands.admin;

import de.beyonddark.VaspelCore.configs.LanguageStrings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvSeeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("vaspelcore.invsee") || sender.isOp()) {
            if (args.length == 1) {
                if (sender instanceof Player) {
                    String name = args[0];
                    if (Bukkit.getPlayer(name) == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6VaspelCore&8]&7 Spieler ist nicht Online!"));
                        return false;
                    }
                    Player target = Bukkit.getPlayer(name);
                    Player player = (Player) sender;
                    if (player == target) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6VaspelCore&8]&7 kannst nicht dein eigenes Inventar angucken!"));
                        return false;
                    }
                    assert target != null;
                    player.openInventory(target.getInventory());
                    return true;
                } else {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6VaspelCore&8]&7 Musst ein Spieler sein!"));
                }
            } else {
                sender.sendMessage("(Player)");
            }
        } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("no-permission")));
        }
        return true;
    }
}
