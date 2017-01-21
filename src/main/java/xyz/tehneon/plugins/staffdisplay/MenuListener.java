package xyz.tehneon.plugins.staffdisplay;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

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

    /**
     * Checks if the provided inventory is the inventory which holds the Staff heads
     *
     * @param otherInventory Inventory which will be checked to be the menu
     * @return Returns true if the provided menu is the same as the Staff heads menu
     */
    public boolean isTheMenu(Inventory otherInventory) {
        return otherInventory.equals(null);
    }
}
