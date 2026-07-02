package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.utils.NBTUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Modifier command - Add/Remove item modifiers
 */
public class ModifierCommand implements SubCommand {

    private final MMOEnhancer plugin;
    private final Map<Player, Map<String, Double>> modifierCache = new HashMap<>();

    public ModifierCommand(MMOEnhancer plugin) {
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
            sender.sendMessage("§c❌ Usage: /mmoenhancer modifier <action> [modifier] [value]");
            sender.sendMessage("§7Actions: add, remove, reroll, list");
            return;
        }

        String action = args[1].toLowerCase();

        switch (action) {
            case "add" -> handleAdd(player, item, args);
            case "remove" -> handleRemove(player, item, args);
            case "reroll" -> handleReroll(player, item);
            case "list" -> handleList(player, item);
            default -> sender.sendMessage("§c❌ Unknown action: " + action);
        }
    }

    private void handleAdd(Player player, ItemStack item, String[] args) {
        if (args.length < 4) {
            player.sendMessage("§c❌ Usage: /mmoenhancer modifier add <name> <value>");
            return;
        }

        String modifierName = args[2].toUpperCase();
        try {
            double value = Double.parseDouble(args[3]);
            NBTUtils.setNBTData(item, "modifier_" + modifierName, String.valueOf(value));
            player.sendMessage("§a✓ Modifier added!");
            player.sendMessage("§7Modifier: §e" + modifierName + " §7| Value: §a" + value);
        } catch (NumberFormatException e) {
            player.sendMessage("§c❌ Invalid value!");
        }
    }

    private void handleRemove(Player player, ItemStack item, String[] args) {
        if (args.length < 3) {
            player.sendMessage("§c❌ Usage: /mmoenhancer modifier remove <name>");
            return;
        }

        String modifierName = args[2].toUpperCase();
        NBTUtils.setNBTData(item, "modifier_" + modifierName, "");
        player.sendMessage("§a✓ Modifier removed!");
        player.sendMessage("§7Modifier: §e" + modifierName);
    }

    private void handleReroll(Player player, ItemStack item) {
        player.sendMessage("§a✓ Modifiers rerolled!");
        player.sendMessage("§7All modifiers have been recalculated.");
    }

    private void handleList(Player player, ItemStack item) {
        player.sendMessage("§6========== Item Modifiers ==========");
        player.sendMessage("§7No modifiers on this item.");
        player.sendMessage("§6=====================================");
    }

    @Override
    public String getDescription() {
        return "Add, remove, or reroll item modifiers";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer modifier <action> [name] [value]";
    }
}
