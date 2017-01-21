package xyz.tehneon.plugins.staffdisplay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * The listener which will prevent any disturbances related to the menu.
 */
public class MenuListener implements Listener {
    private StaffDisplay plugin;

    public MenuListener(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        // TODO: Check if the menu clicked has the title which is given inside the config
    }
}
