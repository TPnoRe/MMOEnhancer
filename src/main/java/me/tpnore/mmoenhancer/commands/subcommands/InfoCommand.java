package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.command.CommandSender;

/**
 * Info command - Show plugin information
 */
public class InfoCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public InfoCommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        sender.sendMessage("§6========== MMOEnhancer Info ==========");
        sender.sendMessage("§eVersion: §f" + plugin.getDescription().getVersion());
        sender.sendMessage("§eAuthor: §f" + String.join(", ", plugin.getDescription().getAuthors()));
        sender.sendMessage("§eDescription: §f" + plugin.getDescription().getDescription());
        sender.sendMessage("§eWebsite: §f" + plugin.getDescription().getWebsite());
        sender.sendMessage("§7");
        sender.sendMessage("§eDependencies:");
        sender.sendMessage("  §7Required: §eMMOItems");
        sender.sendMessage("  §7Optional: §eMythicMobs, MMOCore");
        sender.sendMessage("§6==========================================");
    }

    @Override
    public String getDescription() {
        return "Show plugin information";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer info";
    }
}
