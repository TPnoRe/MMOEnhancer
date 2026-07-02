package me.tpnore.mmoenhancer.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.NamespacedKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for handling NBT data on items
 */
public class NBTUtils {

    private static final MMOEnhancer plugin = MMOEnhancer.getInstance();
    private static final NamespacedKey STAT_KEY = new NamespacedKey(plugin, "mmo_stat");
    private static final NamespacedKey MODIFIER_KEY = new NamespacedKey(plugin, "mmo_modifier");
    private static final NamespacedKey TIER_KEY = new NamespacedKey(plugin, "mmo_tier");
    private static final NamespacedKey GEMSLOT_KEY = new NamespacedKey(plugin, "mmo_gemslot");

    /**
     * Get NBT data from item
     */
    public static String getNBTData(ItemStack item, String key) {
        if (item == null || item.getType().isAir()) return null;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return null;
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        return container.get(new NamespacedKey(plugin, key), PersistentDataType.STRING);
    }

    /**
     * Set NBT data on item
     */
    public static void setNBTData(ItemStack item, String key, String value) {
        if (item == null || item.getType().isAir()) return;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, key), PersistentDataType.STRING, value);
        item.setItemMeta(meta);
    }

    /**
     * Get all stats from item
     */
    public static Map<String, Double> getStats(ItemStack item) {
        Map<String, Double> stats = new HashMap<>();
        
        if (item == null || item.getType().isAir()) return stats;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return stats;
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        
        for (NamespacedKey key : container.getKeys()) {
            if (key.getKey().startsWith("stat_")) {
                String statName = key.getKey().substring(5);
                String value = container.get(key, PersistentDataType.STRING);
                try {
                    stats.put(statName, Double.parseDouble(value));
                } catch (NumberFormatException e) {
                    // Skip invalid values
                }
            }
        }
        
        return stats;
    }

    /**
     * Set stat on item
     */
    public static void setStat(ItemStack item, String stat, double value) {
        if (item == null || item.getType().isAir()) return;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, "stat_" + stat.toLowerCase()), 
                     PersistentDataType.STRING, String.valueOf(value));
        item.setItemMeta(meta);
    }

    /**
     * Get gem slots from item
     */
    public static int getGemSlots(ItemStack item) {
        String slots = getNBTData(item, "gemslots");
        try {
            return Integer.parseInt(slots);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Set gem slots on item
     */
    public static void setGemSlots(ItemStack item, int slots) {
        setNBTData(item, "gemslots", String.valueOf(Math.max(0, slots)));
    }

    /**
     * Add gem slots to item
     */
    public static void addGemSlots(ItemStack item, int amount) {
        int current = getGemSlots(item);
        setGemSlots(item, current + amount);
    }

    /**
     * Get tier from item
     */
    public static String getTier(ItemStack item) {
        return getNBTData(item, "tier");
    }

    /**
     * Set tier on item
     */
    public static void setTier(ItemStack item, String tier) {
        setNBTData(item, "tier", tier.toUpperCase());
    }

    /**
     * Regenerate item NBT to latest format
     */
    public static void regenerateNBT(ItemStack item) {
        if (item == null || item.getType().isAir()) return;
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        
        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(new NamespacedKey(plugin, "regenerated"), 
                     PersistentDataType.LONG, System.currentTimeMillis());
        item.setItemMeta(meta);
    }
}
