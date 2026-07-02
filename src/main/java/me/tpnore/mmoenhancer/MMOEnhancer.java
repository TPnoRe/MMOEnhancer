package me.tpnore.mmoenhancer;

import me.tpnore.mmoenhancer.commands.EnhancerCommandHandler;
import me.tpnore.mmoenhancer.gui.GUIListener;
import me.tpnore.mmoenhancer.utils.ConfigManager;
import me.tpnore.mmoenhancer.utils.Logger;
import me.tpnore.mmoenhancer.utils.ItemStorageManager;
import net.Indyuce.MMOItems.MMOItems;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main plugin class for MMOEnhancer
 * MMOItems Utilities Plugin - Similar to Goop
 */
public class MMOEnhancer extends JavaPlugin {

    private static MMOEnhancer instance;
    private Logger logger;
    private ConfigManager configManager;
    private ItemStorageManager storageManager;
    private GUIListener guiListener;
    private MMOItems mmoItems;

    @Override
    public void onEnable() {
        instance = this;
        logger = new Logger(this);
        
        logger.info("========================================");
        logger.info("MMOEnhancer v" + getDescription().getVersion() + " is starting...");
        logger.info("========================================");
        
        if (!checkDependency("MMOItems")) {
            logger.error("MMOItems plugin not found!");
            logger.error("Please install MMOItems to use this plugin.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        mmoItems = (MMOItems) getServer().getPluginManager().getPlugin("MMOItems");
        logger.info("✓ MMOItems detected!");
        
        if (checkDependency("MythicMobs")) {
            logger.info("✓ MythicMobs detected (optional features enabled)");
        }
        if (checkDependency("MMOCore")) {
            logger.info("✓ MMOCore detected (optional features enabled)");
        }
        
        try {
            configManager = new ConfigManager(this);
            logger.info("✓ Configuration loaded successfully!");
        } catch (Exception e) {
            logger.error("Failed to load configuration!");
            logger.error(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        try {
            storageManager = new ItemStorageManager(this);
            logger.info("✓ Storage manager initialized!");
        } catch (Exception e) {
            logger.error("Failed to initialize storage manager!");
            logger.error(e.getMessage());
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        guiListener = new GUIListener(this);
        getServer().getPluginManager().registerEvents(guiListener, this);
        logger.info("✓ GUI listener registered!");
        
        getCommand("mmoenhancer").setExecutor(new EnhancerCommandHandler(this, guiListener));
        logger.info("✓ Commands registered!");
        
        logger.info("========================================");
        logger.info("✓ MMOEnhancer enabled successfully!");
        logger.info("========================================");
    }

    @Override
    public void onDisable() {
        logger.info("MMOEnhancer is shutting down...");
        if (configManager != null) {
            configManager.saveAll();
        }
        logger.info("MMOEnhancer disabled!");
    }

    /**
     * Check if a plugin dependency is available
     */
    private boolean checkDependency(String pluginName) {
        return getServer().getPluginManager().getPlugin(pluginName) != null;
    }

    /**
     * Get the plugin instance
     */
    public static MMOEnhancer getInstance() {
        return instance;
    }

    /**
     * Get the logger
     */
    public Logger getPluginLogger() {
        return logger;
    }

    /**
     * Get the config manager
     */
    public ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     * Get the storage manager
     */
    public ItemStorageManager getStorageManager() {
        return storageManager;
    }

    /**
     * Get MMOItems plugin instance
     */
    public MMOItems getMMOItems() {
        return mmoItems;
    }
}
