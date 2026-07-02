package me.tpnore.mmoenhancer.utils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Custom logger for MMOEnhancer
 * Provides colored and formatted log messages
 */
public class Logger {

    private final JavaPlugin plugin;
    private static final String PREFIX = "§6[MMOEnhancer]§r ";

    public Logger(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Log an info message
     * @param message Message to log
     */
    public void info(String message) {
        plugin.getLogger().info(message);
    }

    /**
     * Log a warning message
     * @param message Message to log
     */
    public void warn(String message) {
        plugin.getLogger().warning(message);
    }

    /**
     * Log an error message
     * @param message Message to log
     */
    public void error(String message) {
        plugin.getLogger().severe(message);
    }

    /**
     * Log a debug message
     * @param message Message to log
     */
    public void debug(String message) {
        if (isDebugEnabled()) {
            plugin.getLogger().info("[DEBUG] " + message);
        }
    }

    /**
     * Check if debug mode is enabled
     * @return true if debug is enabled, false otherwise
     */
    private boolean isDebugEnabled() {
        // TODO: Read from config file
        return false;
    }

    /**
     * Get the formatted prefix for console messages
     * @return Colored prefix
     */
    public static String getPrefix() {
        return PREFIX;
    }
}