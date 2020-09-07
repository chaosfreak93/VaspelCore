/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore;

import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;
import de.beyonddark.VaspelCore.events.EventListener;
import de.beyonddark.VaspelCore.utils.Enable;
import de.beyonddark.VaspelCore.utils.Load;
import de.beyonddark.VaspelCore.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Main extends JavaPlugin {
    public static Main instance;
    public static List<Player> vanishedPlayer = new ArrayList<>();
    private static Connection con;
    private final Utils utils = new Utils();
    public Map<String, Long> tpaCooldown = new HashMap<>();
    public Map<String, String> currentRequest = new HashMap<>();

    public static Main getInstance() {
        return instance;
    }

    public static void setInstance(Main instance) {
        Main.instance = instance;
    }

    public static void setConnection() throws SQLException, ClassNotFoundException {
        if (con != null && !con.isClosed()) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + MainConfig.get().getString("database") + "?autoReconnect=true&useSSL=false", MainConfig.get().getString("db-user"), MainConfig.get().getString("db-password"));
    }

    public static Connection getConnection() {
        return con;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        setInstance(this);
        Load.setupConfigs();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.getServer().getPluginManager().registerEvents(new EventListener(), this);
        Enable.checkPlayerSleeping();
        Enable.registerCommands();
        Enable.setupWorlds();

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    setConnection();
                } catch (SQLException | ClassNotFoundException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.runTaskAsynchronously(this);
    }

    @Override
    public void onDisable() {
        vanishedPlayer.clear();
        tpaCooldown.clear();
        currentRequest.clear();
        super.onDisable();
    }

    public void sendPlayerHome(Player player, String name) throws SQLException {
        this.utils.sendHome(player, name);
        if (MainConfig.get().getBoolean("play-warp-sound"))
            player.playSound(this.utils.getHomeLocation(player, name), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("teleport-done")).replace("%name%", name)));
    }

    public void setPlayerHome(Player player, String name) throws SQLException {
        if (!this.utils.setHome(player, name)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("sethome")).replace("%name%", name)));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("override-sethome")).replace("%name%", name)));
        }
    }

    public void removePlayerHome(Player player, String name) throws SQLException {
        if (this.utils.removeHome(player, name)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("removehome")).replace("%name%", name)));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("error-removehome")).replace("%name%", name)));
        }
    }

    public void killRequest(String key) {
        if (currentRequest.containsKey(key)) {
            Player loser = getServer().getPlayer(currentRequest.get(key));
            if (loser != null)
                loser.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("tpa-timeout"))));
            currentRequest.remove(key);
        }
    }

    public void sendRequest(Player sender, Player recipient) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("tpa-request")).replace("%player%", recipient.getDisplayName())));
        String sendtpaccept = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("tpa-accept")));
        String sendtpdeny = ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("tpa-deny")));
        recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("tpa")).replace("%player%", sender.getDisplayName())) + "\n" + sendtpaccept + "\n" + sendtpdeny);
        this.currentRequest.put(recipient.getName(), sender.getName());
    }
}
