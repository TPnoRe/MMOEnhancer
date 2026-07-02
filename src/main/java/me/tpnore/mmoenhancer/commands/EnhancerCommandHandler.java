package me.tpnore.mmoenhancer.commands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.commands.subcommands.*;
import me.tpnore.mmoenhancer.gui.GUIListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.HashMap;
import java.util.Map;

/**
 * Main command handler for MMOEnhancer
 */
public class EnhancerCommandHandler implements CommandExecutor {

    private final MMOEnhancer plugin;
    private final Map<String, SubCommand> subCommands;
    private GUIListener guiListener;

    public EnhancerCommandHandler(MMOEnhancer plugin, GUIListener guiListener) {
        this.plugin = plugin;
        this.guiListener = guiListener;
        this.subCommands = new HashMap<>();
        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.put("stat", new StatCommand(plugin));
        subCommands.put("converter", new ConverterCommand(plugin));
        subCommands.put("fixstacks", new FixStacksCommand(plugin));
        subCommands.put("regenerate", new RegenerateCommand(plugin));
        subCommands.put("modifier", new ModifierCommand(plugin));
        subCommands.put("gemslot", new GemSlotCommand(plugin));
        subCommands.put("tier", new TierCommand(plugin));
        
        GUICommand guiCommand = new GUICommand(plugin);
        guiCommand.setGUIListener(guiListener);
        subCommands.put("gui", guiCommand);
        
        subCommands.put("help", new HelpCommand(plugin));
        subCommands.put("info", new InfoCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("mmoenhancer")) {
            return false;
        }

        if (args.length == 0) {
            sendHelpMessage(sender);
            return true;
        }

        String subcommand = args[0].toLowerCase();
        SubCommand subCmd = subCommands.get(subcommand);

        if (subCmd == null) {
            sender.sendMessage("§c❌ Unknown command. Use /mmoenhancer help");
            return true;
        }

        if (!sender.hasPermission("mmoenhancer." + subcommand) && !sender.hasPermission("mmoenhancer.admin")) {
            sender.sendMessage("§c❌ You don't have permission to use this command!");
            return true;
        }

        try {
            subCmd.execute(sender, args);
        } catch (Exception e) {
            sender.sendMessage("§c❌ Error executing command: " + e.getMessage());
            plugin.getPluginLogger().error("Error executing " + subcommand + ": " + e.getMessage());
            e.printStackTrace();
        }

        return true;
    }

    private void sendHelpMessage(CommandSender sender) {
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
}
