package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * FixStacks command - Update MMOItems to latest NBT format
 */
public class FixStacksCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public FixStacksCommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§c❌ This command can only be used by players!");
            return;
        }

        int fixedCount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && !item.getType().isAir()) {
                NBTUtils.regenerateNBT(item);
                fixedCount++;
            }
        }

        sender.sendMessage("§a✓ Fixed all items in your inventory!");
        sender.sendMessage("§7Items updated: §a" + fixedCount);
        sender.sendMessage("§7All MMOItems have been updated to the latest format.");
    }

    @Override
    public String getDescription() {
        return "Update MMOItems to latest NBT format and make them stackable";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer fixstacks";
    }
}
