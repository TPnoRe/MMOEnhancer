package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Regenerate command - Force item NBT update
 */
public class RegenerateCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public RegenerateCommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        if (args.length < 2) {
            sender.sendMessage("§c❌ Usage: /mmoenhancer regenerate <player> [slot]");
            sender.sendMessage("§7Slots: hand, head, chest, legs, feet");
            return;
        }

        String playerName = args[1];
        Player target = Bukkit.getPlayer(playerName);

        if (target == null) {
            sender.sendMessage("§c❌ Player not found: " + playerName);
            return;
        }

        String slot = args.length > 2 ? args[2].toLowerCase() : "hand";
        ItemStack itemToRegenerate = null;

        switch (slot) {
            case "hand" -> itemToRegenerate = target.getInventory().getItemInMainHand();
            case "off" -> itemToRegenerate = target.getInventory().getItemInOffHand();
            case "head" -> itemToRegenerate = target.getEquipment().getHelmet();
            case "chest" -> itemToRegenerate = target.getEquipment().getChestplate();
            case "legs" -> itemToRegenerate = target.getEquipment().getLeggings();
            case "feet" -> itemToRegenerate = target.getEquipment().getBoots();
            default -> {
                sender.sendMessage("§c❌ Unknown slot: " + slot);
                return;
            }
        }

        if (itemToRegenerate == null || itemToRegenerate.getType().isAir()) {
            sender.sendMessage("§c❌ Player has no item in " + slot + " slot!");
            return;
        }

        NBTUtils.regenerateNBT(itemToRegenerate);
        sender.sendMessage("§a✓ Item regenerated successfully!");
        sender.sendMessage("§7Player: §e" + playerName + " §7| Slot: §a" + slot);
        target.sendMessage("§a✓ Your item in " + slot + " slot has been regenerated!");
    }

    @Override
    public String getDescription() {
        return "Force regenerate an item's NBT data";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer regenerate <player> [slot]";
    }
}
