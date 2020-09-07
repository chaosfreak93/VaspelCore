package de.beyonddark.VaspelCore.utils;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.commands.FarmEndCommand;
import de.beyonddark.VaspelCore.commands.FarmNetherCommand;
import de.beyonddark.VaspelCore.commands.SpawnCommand;
import de.beyonddark.VaspelCore.commands.admin.InvSeeCommand;
import de.beyonddark.VaspelCore.commands.admin.ReloadCommand;
import de.beyonddark.VaspelCore.commands.admin.VanishCommand;
import de.beyonddark.VaspelCore.commands.home.*;
import de.beyonddark.VaspelCore.commands.protection.ChestLockCommand;
import de.beyonddark.VaspelCore.commands.tpa.TPACommand;
import de.beyonddark.VaspelCore.commands.tpa.TPATabCompletion;
import de.beyonddark.VaspelCore.commands.tpa.TPAcceptCommand;
import de.beyonddark.VaspelCore.commands.tpa.TPDenyCommand;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.Random;

public class Enable {
    private static final Main plugin = Main.getInstance();

    public static void registerCommands() {
        Objects.requireNonNull(plugin.getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(plugin.getCommand("cl")).setExecutor(new ChestLockCommand());
        Objects.requireNonNull(plugin.getCommand("reloadvaspel")).setExecutor(new ReloadCommand());
        Objects.requireNonNull(plugin.getCommand("sethome")).setExecutor(new SetHomeCommand());
        Objects.requireNonNull(plugin.getCommand("home")).setExecutor(new HomeCommand());
        Objects.requireNonNull(plugin.getCommand("home")).setTabCompleter(new HomeTabCompletion());
        Objects.requireNonNull(plugin.getCommand("removehome")).setExecutor(new RemoveHomeCommand());
        Objects.requireNonNull(plugin.getCommand("delhome")).setExecutor(new RemoveHomeCommand());
        Objects.requireNonNull(plugin.getCommand("removehome")).setTabCompleter(new RemoveHomeTabCompletion());
        Objects.requireNonNull(plugin.getCommand("delhome")).setTabCompleter(new RemoveHomeTabCompletion());
        Objects.requireNonNull(plugin.getCommand("tpa")).setExecutor(new TPACommand());
        Objects.requireNonNull(plugin.getCommand("tpa")).setTabCompleter(new TPATabCompletion());
        Objects.requireNonNull(plugin.getCommand("tpaccept")).setExecutor(new TPAcceptCommand());
        Objects.requireNonNull(plugin.getCommand("tpdeny")).setExecutor(new TPDenyCommand());
        Objects.requireNonNull(plugin.getCommand("invsee")).setExecutor(new InvSeeCommand());
        Objects.requireNonNull(plugin.getCommand("fend")).setExecutor(new FarmEndCommand());
        Objects.requireNonNull(plugin.getCommand("fnether")).setExecutor(new FarmNetherCommand());
        Objects.requireNonNull(plugin.getCommand("vanish")).setExecutor(new VanishCommand());
    }

    public static void checkPlayerSleeping() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (World w : Bukkit.getWorlds()) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p.isSleeping()) {
                            w.setTime(1000);
                            w.setThundering(false);
                            w.setStorm(false);
                        }
                    }
                }
            }
        }.runTaskTimerAsynchronously(plugin, 0, 1);
    }

    public static void setupWorlds() {
        Random rd = new Random();

        WorldCreator nether2 = new WorldCreator("nether_2");
        nether2.environment(World.Environment.NETHER);
        nether2.type(WorldType.NORMAL);
        nether2.seed(rd.nextLong());
        nether2.createWorld();

        WorldCreator wc = new WorldCreator("end_2");
        wc.environment(World.Environment.THE_END);
        wc.type(WorldType.NORMAL);
        wc.seed(rd.nextLong());
        wc.createWorld();

        for (World world : Bukkit.getServer().getWorlds()) {
            world.setDifficulty(Difficulty.NORMAL);
            world.setGameRule(GameRule.KEEP_INVENTORY, true);
            if (world.getName().equals("world")) {
                world.setGameRule(GameRule.DO_FIRE_TICK, false);
            } else {
                world.setGameRule(GameRule.DO_FIRE_TICK, true);
            }
        }
    }
}
