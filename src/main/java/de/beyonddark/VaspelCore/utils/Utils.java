package de.beyonddark.VaspelCore.utils;

import de.beyonddark.VaspelCore.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Utils {

    public static boolean homeIsNull(Player player, String name) throws SQLException {
        Statement st = Main.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT world FROM homes WHERE uuid='" + player.getUniqueId().toString() + "' AND homename='" + name + "';");
        boolean test = rs.next();
        return !test;
    }

    public boolean setHome(Player player, String name) throws SQLException {
        Statement st = Main.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM homes WHERE uuid='" + player.getUniqueId().toString() + "' AND homename='" + name + "';");
        boolean test = rs.next();
        if (test) {
            st.executeUpdate("UPDATE homes SET x = '" + player.getLocation().getX() + "', y = '" + player.getLocation().getY() + "', z = '" + player.getLocation().getZ() + "', yaw = '" + player.getLocation().getYaw() + "', pitch = '" + player.getLocation().getPitch() + "', world = '" + player.getLocation().getWorld().getName() + "' WHERE uuid = '" + player.getUniqueId().toString() + "' AND homename = '" + name + "';");
        } else {
            st.executeUpdate("INSERT INTO homes (uuid, username, homename, x, y, z, yaw, pitch, world) VALUES ('" + player.getUniqueId().toString() + "', '" + player.getDisplayName() + "', '" + name + "', '" + player.getLocation().getX() + "', '" + player.getLocation().getY() + "', '" + player.getLocation().getZ() + "', '" + player.getLocation().getYaw() + "', '" + player.getLocation().getPitch() + "', '" + player.getLocation().getWorld().getName() + "');");
        }
        return test;
    }

    public boolean removeHome(Player player, String name) throws SQLException {
        Statement st = Main.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM homes WHERE uuid='" + player.getUniqueId().toString() + "' AND homename='" + name + "';");
        boolean test = rs.next();
        if (test) {
            st.executeUpdate("DELETE FROM homes WHERE uuid='" + player.getUniqueId().toString() + "' AND homename='" + name + "';");
        }
        return test;
    }

    public void sendHome(Player player, String name) throws SQLException {
        player.teleport(getHomeLocation(player, name));
    }

    public Location getHomeLocation(Player player, String name) throws SQLException {
        Statement st = Main.getConnection().createStatement();
        ResultSet rs = st.executeQuery("SELECT * FROM homes WHERE uuid='" + player.getUniqueId().toString() + "' AND homename='" + name + "';");
        rs.beforeFirst();
        rs.next();
        Location pos = new Location(
                Bukkit.getWorld(rs.getString("world")),
                rs.getFloat("x"),
                rs.getFloat("y"),
                rs.getFloat("z"),
                rs.getFloat("yaw"),
                rs.getFloat("pitch"));
        return pos;
    }
}
