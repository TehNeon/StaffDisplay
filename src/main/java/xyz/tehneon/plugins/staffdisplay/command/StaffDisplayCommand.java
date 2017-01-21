package xyz.tehneon.plugins.staffdisplay.command;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * The class which handles all things orientated to the command.
 */
public class StaffDisplayCommand extends BukkitCommand {
    private StaffDisplay plugin;

    public StaffDisplayCommand(StaffDisplay plugin) {
        super(plugin.getConfig().getString("command.label"));
        setAliases(plugin.getConfig().getStringList("command.aliases"));
        setDescription("Display all the staff of this server.");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (args.length > 0) {
            String subCommand = args[0];
            switch (subCommand.toLowerCase()) {
                case "reload":
                    if (!sender.hasPermission(plugin.getConfig().getString("command.permission") + "reload")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
                        return true;
                    }

                    plugin.reloadConfig();
                    plugin.getMenuBuilder().updateMenu();
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.reloaded")));
                    break;
                case "info":
                    sender.sendMessage(ChatColor.GOLD + "StaffDisplay v" + plugin.getDescription().getVersion());
                    sender.sendMessage(ChatColor.GOLD + " Created by TehNeon.");
                    break;
                default:
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.invalid-subcommand")));
                    break;
            }

            return true;
        }

        if (!sender.hasPermission(plugin.getConfig().getString("command.permission") + "use")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.no-permission")));
            return true;
        }

        // Stop the console and any other source from executing the command as they cannot view/open inventories
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute this portion of the command.");
            return true;
        }

        Player player = (Player) sender;
        player.openInventory(plugin.getMenuBuilder().getInventory());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("messages.opened")));

        return true;
    }
}
