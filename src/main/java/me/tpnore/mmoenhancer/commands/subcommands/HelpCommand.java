package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import org.bukkit.command.CommandSender;

/**
 * Help command - Show help information
 */
public class HelpCommand implements SubCommand {

    private final MMOEnhancer plugin;

    public HelpCommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) throws Exception {
        sender.sendMessage("§6========== MMOEnhancer Help ==========");
        sender.sendMessage("§e/mmoenhancer stat §7- Edit MMOItems stats");
        sender.sendMessage("§e/mmoenhancer converter §7- Convert vanilla to MMOItems");
        sender.sendMessage("§e/mmoenhancer fixstacks §7- Fix and stack MMOItems");
        sender.sendMessage("§e/mmoenhancer regenerate §7- Regenerate item NBT");
        sender.sendMessage("§e/mmoenhancer modifier §7- Manage item modifiers");
        sender.sendMessage("§e/mmoenhancer gemslot §7- Manage gem slots");
        sender.sendMessage("§e/mmoenhancer tier §7- Set item tier");
        sender.sendMessage("§e/mmoenhancer gui §7- Open item editor GUI");
        sender.sendMessage("§e/mmoenhancer help §7- Show this help message");
        sender.sendMessage("§e/mmoenhancer info §7- Show plugin information");
        sender.sendMessage("§6==========================================");
    }

    @Override
    public String getDescription() {
        return "Show help information";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer help";
    }
}
