package me.tpnore.mmoenhancer.gui;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * Advanced GUI for editing items
 */
public class ItemEditorGUI {

    private final MMOEnhancer plugin;
    private final Player player;
    private final ItemStack editingItem;
    private Inventory inventory;

    public ItemEditorGUI(MMOEnhancer plugin, Player player, ItemStack editingItem) {
        this.plugin = plugin;
        this.player = player;
        this.editingItem = editingItem.clone();
    }

    /**
     * Open the editor GUI
     */
    public void open() {
        inventory = Bukkit.createInventory(null, 54, "§6Item Editor - " + editingItem.getType().name());
        
        ItemStack displayItem = editingItem.clone();
        ItemMeta meta = displayItem.getItemMeta();
        if (meta != null) {
            List<String> lore = new ArrayList<>();
            lore.add("§7Current Item");
            meta.setLore(lore);
            displayItem.setItemMeta(meta);
        }
        inventory.setItem(4, displayItem);

        ItemStack statEditor = new ItemStack(Material.REDSTONE);
        meta = statEditor.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e✎ Edit Stats");
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to edit stats");
            meta.setLore(lore);
            statEditor.setItemMeta(meta);
        }
        inventory.setItem(10, statEditor);

        ItemStack modifierEditor = new ItemStack(Material.AMETHYST_SHARD);
        meta = modifierEditor.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e✎ Edit Modifiers");
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to edit modifiers");
            meta.setLore(lore);
            modifierEditor.setItemMeta(meta);
        }
        inventory.setItem(12, modifierEditor);

        ItemStack gemEditor = new ItemStack(Material.DIAMOND);
        meta = gemEditor.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e✎ Edit Gem Slots");
            List<String> lore = new ArrayList<>();
            lore.add("§7Current Gems: §a" + NBTUtils.getGemSlots(editingItem));
            meta.setLore(lore);
            gemEditor.setItemMeta(meta);
        }
        inventory.setItem(14, gemEditor);

        ItemStack tierEditor = new ItemStack(Material.GOLD_BLOCK);
        meta = tierEditor.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§e✎ Edit Tier");
            List<String> lore = new ArrayList<>();
            String tier = NBTUtils.getTier(editingItem);
            lore.add("§7Current Tier: §a" + (tier != null ? tier : "NONE"));
            meta.setLore(lore);
            tierEditor.setItemMeta(meta);
        }
        inventory.setItem(16, tierEditor);

        ItemStack regenerate = new ItemStack(Material.RECOVERY_COMPASS);
        meta = regenerate.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§a⟲ Regenerate NBT");
            List<String> lore = new ArrayList<>();
            lore.add("§7Update to latest format");
            meta.setLore(lore);
            regenerate.setItemMeta(meta);
        }
        inventory.setItem(28, regenerate);

        ItemStack save = new ItemStack(Material.GREEN_CONCRETE);
        meta = save.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§a✓ Save Changes");
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to save all changes");
            meta.setLore(lore);
            save.setItemMeta(meta);
        }
        inventory.setItem(49, save);

        ItemStack cancel = new ItemStack(Material.RED_CONCRETE);
        meta = cancel.getItemMeta();
        if (meta != null) {
            meta.setDisplayName("§c✕ Cancel");
            List<String> lore = new ArrayList<>();
            lore.add("§7Click to cancel");
            meta.setLore(lore);
            cancel.setItemMeta(meta);
        }
        inventory.setItem(50, cancel);

        player.openInventory(inventory);
    }

    /**
     * Get the editing item
     */
    public ItemStack getEditingItem() {
        return editingItem;
    }

    /**
     * Get the inventory
     */
    public Inventory getInventory() {
        return inventory;
    }
}
