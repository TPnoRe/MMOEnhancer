package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * GemSlot command - Manage gem slots on items
 */
public class GemSlotCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public GemSlotCommand(MMOEnhancer plugin) {
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
            int slots = NBTUtils.getGemSlots(item);
            sender.sendMessage("§6========== Gem Slot Info ==========");
            sender.sendMessage("§7Current Gem Slots: §a" + slots);
            sender.sendMessage("§6====================================");
            sender.sendMessage("§7Usage: /mmoenhancer gemslot <action> [amount]");
            sender.sendMessage("§7Actions: add, remove, count, set");
            return;
        }

        String action = args[1].toLowerCase();
        int currentSlots = NBTUtils.getGemSlots(item);
        int amount = args.length > 2 ? Integer.parseInt(args[2]) : 1;

        switch (action) {
            case "add" -> {
                NBTUtils.addGemSlots(item, amount);
                sender.sendMessage("§a✓ Added " + amount + " gem slot(s)!");
                sender.sendMessage("§7Gem slots: §e" + currentSlots + " §7→ §a" + NBTUtils.getGemSlots(item));
            }
            case "remove" -> {
                int newSlots = Math.max(0, currentSlots - amount);
                NBTUtils.setGemSlots(item, newSlots);
                sender.sendMessage("§a✓ Removed " + amount + " gem slot(s)!");
                sender.sendMessage("§7Gem slots: §e" + currentSlots + " §7→ §a" + newSlots);
            }
            case "set" -> {
                NBTUtils.setGemSlots(item, amount);
                sender.sendMessage("§a✓ Set gem slots to " + amount + "!");
                sender.sendMessage("§7Gem slots: §e" + currentSlots + " §7→ §a" + amount);
            }
            case "count" -> {
                sender.sendMessage("§a✓ Current gem slots: §e" + currentSlots);
            }
            default -> sender.sendMessage("§c❌ Unknown action: " + action);
        }
    }

    @Override
    public String getDescription() {
        return "Add, remove, or count gem slots on items";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer gemslot <action> [amount]";
    }
}
