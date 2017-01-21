package xyz.tehneon.plugins.staffdisplay.builder;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TehNeon
 * @since 1/21/2017
 * <p>
 * Class which handles all the creation and building of the menu
 */
public class MenuBuilder {
    private StaffDisplay plugin;
    private Inventory inventory;
    private List<TargetUser> targetUserList = new ArrayList<>();

    public MenuBuilder(StaffDisplay plugin) {
        this.plugin = plugin;

        updateMenu();
    }

    /**
     * Builds/Creates the menu/inventory.
     */
    private void buildMenu() {
        // Create the inventory menu, and the size based off of how many people are viewed/registered as staff
        inventory = Bukkit.createInventory(null, 9 * roundUp(targetUserList.size() / 9d), ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("menu.title")));

        for(TargetUser targetUser: targetUserList) {

        }
    }


    /**
     * Hooks into permissions plugin and searches all users for their group/rank
     * and adds them to the inventory if they are in one of the staff related
     * groups. This also builds the menu right after.
     */
    public void updateMenu() {
        // Clear it from old entries
        targetUserList.clear();

        // Loop through all the ranks inside the config to grab
        for (String rankName : plugin.getConfig().getStringList("ranks")) {
            PermissionGroup permissionGroup = PermissionsEx.getPermissionManager().getGroup(rankName);
            if (permissionGroup != null) {
                for (PermissionUser permissionUser : permissionGroup.getUsers()) {
                    targetUserList.add(new TargetUser(permissionUser.getName(), rankName));
                }
            } else {
                plugin.getLogger().warning("The permission group/rank \"" + rankName + "\" does not seem to exist.");
            }
        }
        buildMenu();
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
        return otherInventory.equals(getInventory()) || otherInventory.getTitle().equalsIgnoreCase(getInventory().getTitle()) && otherInventory.getSize() == getInventory().getSize();
    }

    /**
     * Rounds a value upwards.
     * <p>
     * Found this on StackOverflow a long time ago and don't remember the link to credit for
     *
     * @param d Double which needs to be rounded up
     * @return Returns the value rounded up
     */
    private int roundUp(double d) {
        return (d > (int) d) ? (int) d + 1 : (int) d;
    }
}
