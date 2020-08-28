/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.commands.tpa;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TPATabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();

            for (Player all : Bukkit.getOnlinePlayers()) {
                arguments.add(all.getDisplayName());
            }

            return arguments;
        }

        return null;
    }
}
