package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import net.Indyuce.MMOItems.MMOItems;
import net.Indyuce.MMOItems.api.Type;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Converter command - Convert vanilla items to MMOItems
 */
public class ConverterCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public ConverterCommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c❌ This command can only be used by players!");
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || item.getType().isAir()) {
            sender.sendMessage("§c❌ You must hold an item in your main hand!");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("§c❌ Usage: /mmoenhancer converter <mmoitem_type> <mmoitem_id>");
            sender.sendMessage("§7Example: /mmoenhancer converter SWORD BASIC_SWORD");
            return;
        }

        String typeName = args[1].toUpperCase();
        String itemId = args.length > 2 ? args[2].toUpperCase() : "CONVERTED_ITEM";

        try {
            Type type = MMOItems.plugin.getTypes().get(typeName);
            if (type == null) {
                sender.sendMessage("§c❌ Unknown item type: " + typeName);
                return;
            }

            net.Indyuce.MMOItems.api.item.mmoitem.MMOItem mmoItem = new net.Indyuce.MMOItems.api.item.mmoitem.MMOItem(type, itemId);
            
            if (item.getItemMeta() != null) {
                if (item.getItemMeta().hasDisplayName()) {
                    mmoItem.setName(item.getItemMeta().getDisplayName());
                }
                if (item.getItemMeta().hasLore()) {
                    mmoItem.setLore(item.getItemMeta().getLore());
                }
            }

            ItemStack convertedItem = mmoItem.newBuilder().build();
            player.getInventory().setItemInMainHand(convertedItem);
            
            sender.sendMessage("§a✓ Item converted successfully!");
            sender.sendMessage("§7Type: §e" + typeName + " §7| ID: §a" + itemId);
        } catch (Exception e) {
            sender.sendMessage("§c❌ Error converting item: " + e.getMessage());
            plugin.getPluginLogger().error("Converter error: " + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Convert vanilla items to MMOItems";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer converter <type> <id>";
    }
}
