/*
 * Created by Chaosfreak93
 */

package de.beyonddark.VaspelCore.events;

import de.beyonddark.VaspelCore.Main;
import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;
import de.beyonddark.VaspelCore.utils.Sidebar;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EventListener implements Listener {

    private final Main plugin = Main.getInstance();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
        Player p = e.getPlayer();
        String playerName = p.getName();
        Statement st = Main.getConnection().createStatement();
        ResultSet rangNumber = st.executeQuery("SELECT * FROM players WHERE uuid='" + p.getUniqueId().toString() + "';");
        rangNumber.beforeFirst();
        rangNumber.next();
        ResultSet rangName = st.executeQuery("SELECT * FROM rangs WHERE id='" + rangNumber.getInt("rang") + "';");
        rangName.beforeFirst();
        rangName.next();
        p.setPlayerListName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("playerName")).replace("%player%", playerName).replace("%group%", rangName.getString("name").toUpperCase())));
        p.setCustomName(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("playerName")).replace("%player%", playerName).replace("%group%", rangName.getString("name").toUpperCase())));
        p.setCustomNameVisible(true);
        e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("join-message")).replace("%player%", p.getDisplayName())));
        Sidebar s = new Sidebar();
        s.setup(p);

        for (Player vanish : Main.vanishedPlayer) {
            if (!(p.hasPermission("vaspelcore.vanish.see")) || !(p.isOp())) {
                p.hidePlayer(plugin, vanish);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("leave-message")).replace("%player%", p.getDisplayName())));
        Main.vanishedPlayer.remove(p);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) throws SQLException {
        Player p = e.getPlayer();
        String playerName = p.getName();
        String message = e.getMessage();
        Statement st = Main.getConnection().createStatement();
        ResultSet rangNumber = st.executeQuery("SELECT * FROM players WHERE uuid='" + p.getUniqueId().toString() + "';");
        rangNumber.beforeFirst();
        rangNumber.next();
        ResultSet rangName = st.executeQuery("SELECT * FROM rangs WHERE id='" + rangNumber.getInt("rang") + "';");
        rangName.beforeFirst();
        rangName.next();
        e.setFormat(ChatColor.translateAlternateColorCodes('&', Objects.requireNonNull(LanguageStrings.get().getString("chat-message")).replace("%player%", playerName).replace("%msg%", message).replace("%group%", rangName.getString("name").toUpperCase())));
        st.close();
    }

    @EventHandler
    public void onBlockPLace(BlockPlaceEvent e) {
        Block b = e.getBlock();
        World w = b.getWorld();
        if (b.getType() == Material.TNT) {
            e.setCancelled(!w.getName().equals("world_nether") && !w.getName().equals("nether_2"));
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent e) {
        EntityType entity = e.getEntityType();
        World w = e.getEntity().getWorld();
        if (entity == EntityType.PRIMED_TNT) {
            e.setCancelled(!w.getName().equals("world_nether") && !w.getName().equals("nether_2"));
        }
        if (entity == EntityType.MINECART_TNT) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        EntityType entity = e.getEntityType();
        World w = e.getEntity().getWorld();
        if (entity == EntityType.PRIMED_TNT) {
            e.setCancelled(!w.getName().equals("world_nether") && !w.getName().equals("nether_2"));
        } else if (entity == EntityType.ENDER_CRYSTAL) {
            e.setCancelled(!w.getName().equals("world_the_end") && !w.getName().equals("end_2"));
        }
        if (entity == EntityType.CREEPER || entity == EntityType.MINECART_TNT) {
            e.setCancelled(true);
        }
    }

    private ItemStack getBook() {
        final ItemStack writtenBook = new ItemStack(Material.WRITTEN_BOOK, 1);
        final BookMeta bookMeta = (BookMeta) writtenBook.getItemMeta();
        assert bookMeta != null;
        bookMeta.setTitle(ChatColor.GOLD + "Infos");
        bookMeta.setAuthor("__keiner__");
        List<String> pages = new ArrayList<String>();
        pages.add("Hallo und willkommen auf " + ChatColor.GOLD + "Vaspel's Server!\n\n" + ChatColor.BLACK + "Im diesem Buch werden dir Grundlegende Sachen erklärt!\n\nViel Spaß auf dem Minecraft Server!");
        pages.add("Inhaltsverseichnis:\n- " + ChatColor.GOLD + "Regelwerk\n" + ChatColor.BLACK + "- " + ChatColor.GOLD + "Anti-Griefing\n" + ChatColor.BLACK + "- " + ChatColor.GOLD + "Befehle\n" + ChatColor.BLACK + "- " + ChatColor.GOLD + "Extras");
        pages.add(ChatColor.GOLD + "Regeln:\n\n" + ChatColor.BLACK + "- Bitte baue im Stil der anderen sonst entferne dich bitte etwas weiter vom Dorf.\n\n- Griefing/Trolling ist nicht erlaubt.\n\n- Hacking/Cheating ist nicht erlaubt.");
        pages.add(ChatColor.GOLD + "Anti-Griefing:\n\n" + ChatColor.BLACK + "- TnT klappt nur im Nether oder End.\n\n- Tnt Minecarts sind deaktiviert.\n\n- Creeper machen keinen Blockschaden.\n\n- PvP ist nur Mittwochs aktiv.");
        pages.add("- End Kristalle machen nur im End Blockschaden.\n\n- Fire Tick ist aus");
        pages.add(ChatColor.GOLD + "Commands:\n\n" + ChatColor.BLACK + "Im folgendem Abschnitt werden dir ein paar Commands erklärt!");
        pages.add(ChatColor.GOLD + "/spawn\n" + ChatColor.BLACK + "Teleportiert dich zum Spawn");
        pages.add(ChatColor.GOLD + "/sethome <name>\n" + ChatColor.BLACK + "Hier mit kannst du dir ein Home setzen!\n\n" + ChatColor.GOLD + "/delhome <name>\n" + ChatColor.BLACK + "Hier mit löschst du ein vorhandenes Home!\n\n" + ChatColor.GOLD + "/home <name>\n" + ChatColor.BLACK + "Hier mit kannst du dich zu deinem Home teleportieren!");
        pages.add(ChatColor.GOLD + "/listhome\n" + ChatColor.BLACK + "Zeigt dir deine Homes an!");
        pages.add(ChatColor.GOLD + "/tpa <player>\n" + ChatColor.BLACK + "Hier mit sendest du eine Teleport Anfrage an denn Angegebenen Spieler!\n\n" + ChatColor.GOLD + "/tpaccept\n" + ChatColor.BLACK + "Hier mit nimmst du die aktuelle Teleport Anfrage an!");
        pages.add(ChatColor.GOLD + "/tpdeny\n" + ChatColor.BLACK + "Hier mit lehnst du die aktuelle Teleport Anfrage ab!");
        pages.add(ChatColor.GOLD + "/fend\n" + ChatColor.BLACK + "Hier mit kommst du in das Farm End!\n\n" + ChatColor.GOLD + "/fnether\n" + ChatColor.BLACK + "Hier mit kommst du in denn Farm Nether!");
        pages.add(ChatColor.GOLD + "Extras:\n\n" + ChatColor.BLACK + "- Es muss nur eine Person schlafen das es Tag wird\n\n- Keep Inventory ist an");
        bookMeta.setPages(pages);
        writtenBook.setItemMeta(bookMeta);
        return writtenBook;
    }
}
