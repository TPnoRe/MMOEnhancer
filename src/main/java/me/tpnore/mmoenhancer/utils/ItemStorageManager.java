package me.tpnore.mmoenhancer.utils;

import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Manages item storage in YAML format
 */
public class ItemStorageManager {

    private final MMOEnhancer plugin;
    private final Path storageDir;
    private final Yaml yaml;

    public ItemStorageManager(MMOEnhancer plugin) throws IOException {
        this.plugin = plugin;
        this.yaml = new Yaml();
        this.storageDir = Paths.get(plugin.getDataFolder().getAbsolutePath(), "storage");
        
        if (!Files.exists(storageDir)) {
            Files.createDirectories(storageDir);
        }
    }

    /**
     * Save item to storage
     */
    public void saveItem(String id, ItemStack item) throws IOException {
        Map<String, Object> data = new HashMap<>();
        data.put("item", serializeItem(item));
        data.put("saved_at", System.currentTimeMillis());

        Path filePath = storageDir.resolve(id + ".yml");
        try (FileWriter writer = new FileWriter(filePath.toFile())) {
            yaml.dump(data, writer);
        }
    }

    /**
     * Load item from storage
     */
    public ItemStack loadItem(String id) throws IOException {
        Path filePath = storageDir.resolve(id + ".yml");
        
        if (!Files.exists(filePath)) {
            return null;
        }

        try (FileReader reader = new FileReader(filePath.toFile())) {
            Map<String, Object> data = yaml.load(reader);
            if (data != null && data.containsKey("item")) {
                return deserializeItem((String) data.get("item"));
            }
        }
        
        return null;
    }

    /**
     * Serialize item to string
     */
    private String serializeItem(ItemStack item) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (BukkitObjectOutputStream oos = new BukkitObjectOutputStream(baos)) {
            oos.writeObject(item);
        }
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * Deserialize item from string
     */
    private ItemStack deserializeItem(String data) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(Base64.getDecoder().decode(data));
        try (BukkitObjectInputStream ois = new BukkitObjectInputStream(bais)) {
            return (ItemStack) ois.readObject();
        } catch (ClassNotFoundException e) {
            throw new IOException("Failed to deserialize item", e);
        }
    }

    /**
     * Delete item storage
     */
    public void deleteItem(String id) throws IOException {
        Path filePath = storageDir.resolve(id + ".yml");
        Files.deleteIfExists(filePath);
    }
}
