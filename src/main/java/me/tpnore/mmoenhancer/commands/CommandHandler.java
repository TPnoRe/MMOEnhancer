package me.tpnore.mmoenhancer.commands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Command handler for MMOEnhancer commands
 */
public class CommandHandler implements CommandExecutor {

    private final MMOEnhancer plugin;

    public CommandHandler(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mmo")) {
            return false;
        }

        // If no args, show help
        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "help" -> sendHelpMessage(sender);
            case "info" -> sendInfoMessage(sender);
            case "reload" -> handleReload(sender);
            case "status" -> handleStatus(sender);
            default -> sender.sendMessage("§cUnknown command. Use /mmo help for help.");
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
        sender.sendMessage("§6========== MMOEnhancer Help ==========");
        sender.sendMessage("§e/mmo help §7- Show this help message");
        sender.sendMessage("§e/mmo info §7- Show plugin information");
        sender.sendMessage("§e/mmo status §7- Show plugin status");
        sender.sendMessage("§e/mmo reload §7- Reload the plugin (Admin only)");
        sender.sendMessage("§6==========================================");
    }

    private void sendInfoMessage(CommandSender sender) {
        sender.sendMessage("§6========== MMOEnhancer Info ==========");
        sender.sendMessage("§eVersion: §f" + plugin.getDescription().getVersion());
        sender.sendMessage("§eAuthor: §f" + String.join(", ", plugin.getDescription().getAuthors()));
        sender.sendMessage("§eWebsite: §f" + plugin.getDescription().getWebsite());
        sender.sendMessage("§6==========================================");
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("mmo.admin")) {
            sender.sendMessage("§cYou don't have permission to use this command!");
            return;
        }

        sender.sendMessage("§eReloading MMOEnhancer...");
        plugin.onDisable();
        plugin.onEnable();
        sender.sendMessage("§aMMOEnhancer reloaded successfully! ✓");
    }

    private void handleStatus(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cThis command can only be used by players!");
            return;
        }

        sender.sendMessage("§6========== MMOEnhancer Status ==========");
        sender.sendMessage("§ePlayer: §f" + player.getName());
        sender.sendMessage("§eLevel: §f" + player.getLevel());
        sender.sendMessage("§eHealth: §f" + String.format("%.1f", player.getHealth()) + " / " + String.format("%.1f", player.getMaxHealth()));
        sender.sendMessage("§6==========================================");
    }
}