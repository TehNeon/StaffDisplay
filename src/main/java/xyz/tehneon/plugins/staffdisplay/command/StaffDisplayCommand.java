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
        super("staffdisplay");
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players may execute this command.");
            return true;
        }

        return true;
    }
}
