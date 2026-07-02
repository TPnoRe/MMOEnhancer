package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

/**
 * Stat command - Edit MMOItems stats
 */
public class StatCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public StatCommand(MMOEnhancer plugin) {
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
            sender.sendMessage("§6========== Item Stats ==========");
            Map<String, Double> stats = NBTUtils.getStats(item);
            if (stats.isEmpty()) {
                sender.sendMessage("§7No stats on this item.");
            } else {
                for (Map.Entry<String, Double> entry : stats.entrySet()) {
                    sender.sendMessage("§e" + entry.getKey() + ": §a" + entry.getValue());
                }
            }
            sender.sendMessage("§6================================");
            sender.sendMessage("§7Usage: /mmoenhancer stat <stat> <value>");
            return;
        }

        String statName = args[1].toUpperCase();
        double value;
        
        try {
            value = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("§c❌ Invalid value. Must be a number!");
            return;
        }

        NBTUtils.setStat(item, statName, value);
        sender.sendMessage("§a✓ Stat set successfully!");
        sender.sendMessage("§7Stat: §e" + statName + " §7| Value: §a" + value);
    }

    @Override
    public String getDescription() {
        return "Edit MMOItems stats in real time";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer stat <stat> <value>";
    }
}
