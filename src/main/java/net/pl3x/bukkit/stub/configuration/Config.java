package net.pl3x.bukkit.stub.configuration;

import net.pl3x.bukkit.stub.StubPlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    public static boolean COLOR_LOGS = true;
    public static boolean DEBUG_MODE = false;
    public static String LANGUAGE_FILE = "lang-en.yml";

    public static void reload() {
        StubPlugin plugin = StubPlugin.getPlugin();
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        FileConfiguration config = plugin.getConfig();

        COLOR_LOGS = config.getBoolean("color-logs", true);
        DEBUG_MODE = config.getBoolean("debug-mode", false);
        LANGUAGE_FILE = config.getString("language-file", "lang-en.yml");
    }
}
