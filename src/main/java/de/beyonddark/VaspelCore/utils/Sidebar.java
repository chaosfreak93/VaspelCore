/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.utils;

import de.beyonddark.VaspelCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class Sidebar {

    private final Main plugin = Main.getInstance();

    public void setup(Player p) {
        ScoreboardManager manager = Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        Objective objective = scoreboard.registerNewObjective("title", "dummy", ChatColor.DARK_BLUE + Bukkit.getServer().getMotd());
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        Team placeHolder = scoreboard.registerNewTeam("placeHolder");
        placeHolder.addEntry("");
        placeHolder.setSuffix("");
        placeHolder.setPrefix("");
        objective.getScore("").setScore(2);
        Team playerCount = scoreboard.registerNewTeam("playerCount");
        playerCount.addEntry("§6Online: ");
        playerCount.setSuffix("§70");
        playerCount.setPrefix("");
        objective.getScore("§6Online: ").setScore(1);
        new BukkitRunnable() {
            @Override
            public void run() {
                playerCount.setSuffix("§7" + Bukkit.getServer().getOnlinePlayers().size());
            }
        }.runTaskTimerAsynchronously(plugin, 0, 1);

        p.setScoreboard(scoreboard);
    }
}
