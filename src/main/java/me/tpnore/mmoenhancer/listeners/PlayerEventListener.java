package me.tpnore.mmoenhancer.listeners;

import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;

/**
 * Event listener for player events in MMOEnhancer
 */
public class PlayerEventListener implements Listener {

    private final MMOEnhancer plugin;

    public PlayerEventListener(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getPluginLogger().info(player.getName() + " joined the server!");
        
        // Load player data from database
        try {
            // TODO: Load player data from database
        } catch (Exception e) {
            plugin.getPluginLogger().error("Error loading player data for " + player.getName() + ": " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        plugin.getPluginLogger().info(player.getName() + " left the server!");
        
        // Save player data to database
        try {
            // TODO: Save player data to database
        } catch (Exception e) {
            plugin.getPluginLogger().error("Error saving player data for " + player.getName() + ": " + e.getMessage());
        }
    }

    @EventHandler
    public void onPlayerLevelChange(PlayerLevelChangeEvent event) {
        Player player = event.getPlayer();
        int newLevel = event.getNewLevel();
        
        plugin.getPluginLogger().info(player.getName() + " reached level " + newLevel + "!");
        
        // TODO: Handle level up rewards or events
    }
}