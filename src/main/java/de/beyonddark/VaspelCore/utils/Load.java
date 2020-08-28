package de.beyonddark.VaspelCore.utils;

import de.beyonddark.VaspelCore.configs.LanguageStrings;
import de.beyonddark.VaspelCore.configs.MainConfig;

import java.io.IOException;

public class Load {

    public static void setupConfigs() {
        try {
            MainConfig.setup();
            MainConfig.set().addDefault("play-warp-sound", true);
            MainConfig.set().addDefault("home-command-delay", true);
            MainConfig.set().addDefault("home-time-delay", 3);
            MainConfig.set().addDefault("tpa-cooldown", 3);
            MainConfig.set().addDefault("keep-alive", 30);
            MainConfig.set().addDefault("first-join-book", false);
            MainConfig.set().addDefault("db-user", "Zeo");
            MainConfig.set().addDefault("db-password", "Gommekiller93");
            MainConfig.set().addDefault("database", "Vaspel");
            MainConfig.get().options().copyDefaults(true);
            MainConfig.save();

            LanguageStrings.setup();
            LanguageStrings.set().addDefault("reload", "&8[&6VaspelCore&8]&7 Config's neugeladen!");
            LanguageStrings.set().addDefault("playerName", "&8[&6%group%&8] &7%player%");
            LanguageStrings.set().addDefault("join-message", "&a>> &7%player%");
            LanguageStrings.set().addDefault("leave-message", "&c<< &7%player%");
            LanguageStrings.set().addDefault("chat-message", "&8[&6%group%&8] &7%player% &6>> &7%msg%");
            LanguageStrings.set().addDefault("no-permission", "&8[&6VaspelCore&8]&7 Du hast keine Rechte dazu!");
            LanguageStrings.set().addDefault("only-player", "&8[&6VaspelCore&8]&7 Nur &6Spieler&7 können diesen Command benutzen!");
            LanguageStrings.set().addDefault("spawn", "&8[&6VaspelCore&8]&7 Du wurdest zum &6Spawn&7 teleportiert!");
            LanguageStrings.set().addDefault("fnether", "&8[&6VaspelCore&8]&7 Du wurdest zum &6Farm Nether&7 teleportiert!");
            LanguageStrings.set().addDefault("fend", "&8[&6VaspelCore&8]&7 Du wurdest zum &6Farm End&7 teleportiert!");
            LanguageStrings.set().addDefault("sethome", "&8[&6VaspelCore&8]&7 Du hast das Haus &6%name%&7 gesetzt!");
            LanguageStrings.set().addDefault("override-sethome", "&8[&6VaspelCore&8]&7 Das vorhandene Haus &6%name%&7 wurde überschrieben!");
            LanguageStrings.set().addDefault("removehome", "&8[&6VaspelCore&8]&7 Du hast das Haus &6%name%&7 gelöscht!");
            LanguageStrings.set().addDefault("must-sethome", "&8[&6VaspelCore&8]&7 Du musst zuerst mit &6/sethome <name>&7 ein Home setzen!");
            LanguageStrings.set().addDefault("missing-name-sethome", "&8[&6VaspelCore&8]&7 Bitte gebe einen Namen für den Home an, &6/sethome <name>&7!");
            LanguageStrings.set().addDefault("missing-name-removehome", "&8[&6VaspelCore&8]&7 Bitte gebe einen Namen für den Home an, &6/sethome <name>&7!");
            LanguageStrings.set().addDefault("error-removehome", "&8[&6VaspelCore&8]&7 Das Haus &6%name%&7 existiert nicht!");
            LanguageStrings.set().addDefault("missing-name-home", "&8[&6VaspelCore&8]&7 &7Du musst denn Namen des Home mit angeben, &6/home <name>&7!");
            LanguageStrings.set().addDefault("teleport-soon", "&8[&6VaspelCore&8]&7 Du wirst in &6%seconds%&7 Sekunden zu &6%name%&7 teleportiert!");
            LanguageStrings.set().addDefault("teleport-done", "&8[&6VaspelCore&8]&7 Du wurdest zu &6%name%&7 teleportiert!");
            LanguageStrings.set().addDefault("teleport-tpa", "&8[&6VaspelCore&8]&7 Teleportiert...");
            LanguageStrings.set().addDefault("player-offline", "&8[&6VaspelCore&8]&7 ");
            LanguageStrings.set().addDefault("no-requests", "&8[&6VaspelCore&8]&7 Du hast momentan keine Teleport Anfragen");
            LanguageStrings.set().addDefault("tpa-timeout", "&8[&6VaspelCore&8]&7 Die Zeit der Teleport Anfrage ist abgelaufen!");
            LanguageStrings.set().addDefault("tp-yourself", "&8[&6VaspelCore&8]&7 Du kannst dich nicht zu dir selber teleportieren!");
            LanguageStrings.set().addDefault("only-online-player-tp", "&8[&6VaspelCore&8]&7 Du kannst dich nur zu Spielern Teleportieren die online sind!");
            LanguageStrings.set().addDefault("tpa-cooldown", "&8[&6VaspelCore&8]&7 Du musst &6%cooldown%&7 Sekunden zwischen einer Teleport Anfrage warten!");
            LanguageStrings.set().addDefault("tpa-rejected", "&8[&6VaspelCore&8]&7 Deine Teleport Anfrage an &6%player%&7 wurde abgelehnt!");
            LanguageStrings.set().addDefault("tpa-request-rejected", "&8[&6VaspelCore&8]&7 Du hast die Teleport Anfrage von &6%player%&7 abgelehnt!");
            LanguageStrings.set().addDefault("tpa-request", "&8[&6VaspelCore&8]&7 Die Teleport Anfrage an &6%player%&7 wurde gesendet!");
            LanguageStrings.set().addDefault("tpa-accept", " Mit &6/tpaccept&7 kannst du diese Teleport Anfrage annehmen!");
            LanguageStrings.set().addDefault("tpa-deny", " Mit &6/tpdeny&7 kannst du diese Teleport Anfrage ablehnen!");
            LanguageStrings.set().addDefault("tpa", "&8[&6VaspelCore&8]&7 Du hast eine Teleport Anfrage von &6%player%&7 erhalten:");
            LanguageStrings.get().options().copyDefaults(true);
            LanguageStrings.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
