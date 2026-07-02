package me.tpnore.mmoenhancer.commands.subcommands;

import me.tpnore.mmoenhancer.MMOEnhancer;
import me.tpnore.mmoenhancer.gui.GUIListener;
import me.tpnore.mmoenhancer.gui.ItemEditorGUI;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * GUI command - Open item editor GUI
 */
public class GUICommand implements SubCommand {

    private final MMOEnhancer plugin;
    private GUIListener guiListener;

    public GUICommand(MMOEnhancer plugin) {
        this.plugin = plugin;
    }

    public void setGUIListener(GUIListener listener) {
        this.guiListener = listener;
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

        ItemEditorGUI gui = new ItemEditorGUI(plugin, player, item);
        
        if (guiListener != null) {
            guiListener.registerEditor(player, gui);
        }
        
        gui.open();
        sender.sendMessage("§a✓ Item editor GUI opened!");
    }

    @Override
    public String getDescription() {
        return "Open the advanced item editor GUI";
    }

    @Override
    public String getUsage() {
        return "/mmoenhancer gui";
    }
}
