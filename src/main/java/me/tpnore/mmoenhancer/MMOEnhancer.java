package me.tpnore.mmoenhancer;

import me.tpnore.mmoenhancer.commands.CommandHandler;
import me.tpnore.mmoenhancer.database.DatabaseManager;
import me.tpnore.mmoenhancer.listeners.PlayerEventListener;
import me.tpnore.mmoenhancer.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for MMOEnhancer
 * Handles initialization and management of the plugin
 */
public class MMOEnhancer extends JavaPlugin {

    private static MMOEnhancer instance;
    private DatabaseManager databaseManager;
    private Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = new Logger(this);
        
        // Log startup
        logger.info("MMOEnhancer v" + getDescription().getVersion() + " is starting...");
        
        // Initialize database
        try {
            databaseManager = new DatabaseManager(this);
            logger.info("Database initialized successfully!");
        } catch (Exception e) {
            logger.error("Failed to initialize database!");
            logger.error(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        // Register commands
        getCommand("mmo").setExecutor(new CommandHandler(this));
        logger.info("Commands registered!");
        
        // Register event listeners
        getServer().getPluginManager().registerEvents(new PlayerEventListener(this), this);
        logger.info("Event listeners registered!");
        
        logger.info("MMOEnhancer enabled successfully! ✓");
    }

    @Override
    public void onDisable() {
        logger.info("MMOEnhancer is shutting down...");
        
        // Close database connection
        if (databaseManager != null) {
            try {
                databaseManager.close();
                logger.info("Database connection closed!");
            } catch (Exception e) {
                logger.error("Error closing database: " + e.getMessage());
            }
        }
        
        logger.info("MMOEnhancer disabled!");
    }

    /**
     * Get the plugin instance
     * @return MMOEnhancer plugin instance
     */
    public static MMOEnhancer getInstance() {
        return instance;
    }

    /**
     * Get the database manager
     * @return DatabaseManager instance
     */
    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    /**
     * Get the logger
     * @return Logger instance
     */
    public Logger getPluginLogger() {
        return logger;
    }
}