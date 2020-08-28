package de.beyonddark.VaspelCore.commands.home;

import de.beyonddark.VaspelCore.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RemoveHomeTabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;

        if (args.length == 1) {
            List<String> arguments = new ArrayList<>();


            ResultSet rs;
            try {
                Statement st = Main.getConnection().createStatement();
                rs = st.executeQuery("SELECT * FROM homes WHERE uuid='" + p.getUniqueId().toString() + "';");

                while (rs.next()) {
                    String homes = rs.getString("homename");
                    arguments.add(homes);
                }
                st.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            return arguments;
        }

        return null;
    }
}
