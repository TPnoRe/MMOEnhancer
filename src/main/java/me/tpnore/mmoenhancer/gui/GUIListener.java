package me.tpnore.mmoenhancer.gui;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Listens to GUI interactions
 */
public class GUIListener implements Listener {

    private final MMOEnhancer plugin;
    private final Map<Player, ItemEditorGUI> activeEditors = new HashMap<>();

    public GUIListener(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        Player player = (Player) event.getWhoClicked();
        
        if (!event.getView().getTitle().contains("Item Editor")) return;
        
        event.setCancelled(true);
        
        int slot = event.getRawSlot();
        ItemEditorGUI editor = activeEditors.get(player);
        if (editor == null) return;

        ItemStack editingItem = editor.getEditingItem();

        switch (slot) {
            case 10:
                player.sendMessage("§a✓ Stat editor opened in chat (TODO: Implement chat editor)");
                break;
            case 12:
                player.sendMessage("§a✓ Modifier editor opened in chat (TODO: Implement chat editor)");
                break;
            case 14:
                int currentGems = NBTUtils.getGemSlots(editingItem);
                NBTUtils.addGemSlots(editingItem, 1);
                player.sendMessage("§a✓ Added 1 gem slot! (" + currentGems + " → " + NBTUtils.getGemSlots(editingItem) + ")");
                break;
            case 16:
                NBTUtils.setTier(editingItem, "LEGENDARY");
                player.sendMessage("§a✓ Set tier to LEGENDARY");
                break;
            case 28:
                NBTUtils.regenerateNBT(editingItem);
                player.sendMessage("§a✓ Item regenerated to latest format!");
                break;
            case 49:
                ItemStack cursor = event.getCursor();
                if (cursor != null && !cursor.getType().isAir()) {
                    cursor.setItemMeta(editingItem.getItemMeta());
                    player.sendMessage("§a✓ Changes saved to item!");
                }
                player.getInventory().setItemInMainHand(editingItem);
                activeEditors.remove(player);
                player.closeInventory();
                break;
            case 50:
                activeEditors.remove(player);
                player.closeInventory();
                player.sendMessage("§c✕ Editor cancelled");
                break;
        }
    }

    /**
     * Register an active editor
     */
    public void registerEditor(Player player, ItemEditorGUI editor) {
        activeEditors.put(player, editor);
    }

    /**
     * Unregister an editor
     */
    public void unregisterEditor(Player player) {
        activeEditors.remove(player);
    }
}
