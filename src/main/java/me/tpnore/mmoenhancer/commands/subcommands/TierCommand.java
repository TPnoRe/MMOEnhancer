package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Tier command - Get or set the tier of MMOItems
 */
public class TierCommand implements SubCommand {

    private final MMOEnhancer plugin;
    private static final String[] TIERS = {"COMMON", "UNCOMMON", "RARE", "EPIC", "LEGENDARY", "MYTHIC"};

    public TierCommand(MMOEnhancer plugin) {
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
            String currentTier = NBTUtils.getTier(item);
            sender.sendMessage("§6========== Item Tier ==========");
            sender.sendMessage("§7Current Tier: §a" + (currentTier != null ? currentTier : "NONE"));
            sender.sendMessage("§6Available Tiers:");
            for (String tier : TIERS) {
                sender.sendMessage("  §7- §e" + tier);
            }
            sender.sendMessage("§6==============================");
            sender.sendMessage("§7Usage: /mmoenhancer tier <tier_name>");
            return;
        }

        String tier = args[1].toUpperCase();
        
        boolean validTier = false;
        for (String t : TIERS) {
            if (t.equals(tier)) {
                validTier = true;
                break;
            }
        }

        if (!validTier) {
            sender.sendMessage("§c❌ Invalid tier: " + tier);
            sender.sendMessage("§7Valid tiers: " + String.join(", ", TIERS));
            return;
        }

        String oldTier = NBTUtils.getTier(item);
        NBTUtils.setTier(item, tier);
        
        sender.sendMessage("§a✓ Tier set successfully!");
        sender.sendMessage("§7Tier: §e" + (oldTier != null ? oldTier : "NONE") + " §7→ §a" + tier);
    }

    @Override
    public String getDescription() {
        return "Get or set the tier of MMOItems";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer tier [tier_name]";
    }
}
