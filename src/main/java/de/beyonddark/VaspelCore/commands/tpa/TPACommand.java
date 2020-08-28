package de.beyonddark.VaspelCore.commands.tpa;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TPACommand implements CommandExecutor {

    private final Main plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (p != null) {
            int cooldown = MainConfig.get().getInt("tpa-cooldown");
            if (plugin.tpaCooldown.containsKey(p.getName())) {
                long diff = (System.currentTimeMillis() - plugin.tpaCooldown.get(sender.getName())) / 1000L;
                if (diff < cooldown) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("tpa-cooldown").replace("%cooldown%", "" + cooldown)));
                    return false;
                }
            }
            if (args.length > 0) {
                final Player target = plugin.getServer().getPlayer(args[0]);
                long keepAlive = MainConfig.get().getLong("keep-alive") * 20L;
                if (target == null) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("only-online-player-tp")));
                    return false;
                }
                if (target == p) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("tp-yourself")));
                    return false;
                }
                plugin.sendRequest(p, target);
                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> plugin.killRequest(target.getName()), keepAlive);
                plugin.tpaCooldown.put(p.getName(), System.currentTimeMillis());
            } else {
                p.sendMessage("/tpa <player>");
            }
        } else {
            assert sender != null;
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LanguageStrings.get().getString("tp-yourself")));
            return false;
        }
        return true;
    }
}
