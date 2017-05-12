package xyz.tehneon.plugins.staffdisplay.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;

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
        if (event.getClickedInventory() != null) {
            if (this.plugin.getMenuBuilder().isTheMenu(event.getClickedInventory())) {
                event.setCancelled(true);
            }
        }
    }
}
