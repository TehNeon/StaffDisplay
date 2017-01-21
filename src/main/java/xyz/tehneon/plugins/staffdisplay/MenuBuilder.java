package xyz.tehneon.plugins.staffdisplay;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * Class which handles all the creation and building of the menu
 */
public class MenuBuilder {
    private StaffDisplay plugin;
    private Inventory inventory;

    public MenuBuilder(StaffDisplay plugin) {
        this.plugin = plugin;

        buildMenu();
    }

    /**
     * Builds/Creates the menu/inventory.
     */
    private void buildMenu() {
        inventory = Bukkit.createInventory(null, 9, ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("menu.title")));
    }


    /**
     * Hooks into permissions plugin and searches all users for their group/rank
     * and adds them to the inventory if they are in one of the staff related
     * groups.
     */
    public void updateMenu() {

    }


    /**
     * Get the inventory which contains all the staff members
     *
     * @return Inventory/menu which contains all the staff members heads
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Checks if the provided inventory is the inventory which holds the Staff heads
     *
     * @param otherInventory Inventory which will be checked to be the menu
     * @return Returns true if the provided menu is the same as the Staff heads menu
     */
    public boolean isTheMenu(Inventory otherInventory) {
        return otherInventory.equals(getInventory());
    }
}
