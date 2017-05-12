package xyz.tehneon.plugins.staffdisplay.builder;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
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

        // Run the update 2 ticks after startup so that all plugins are loaded properly, allowing for Hooks to properly be set in place
        new BukkitRunnable() {
            public void run() {
                MenuBuilder.this.updateMenu();
            }
        }.runTaskLater(plugin, 2L);
    }

    /**
     * Builds/Creates the menu/inventory.
     */
    private void buildMenu() {
        // Create the inventory menu, and the size based off of how many people are viewed/registered as staff
        this.inventory = Bukkit.createInventory(null, 9 * roundUp(this.targetUserList.size() / 9d), ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("menu.title")));

        for (TargetUser targetUser : this.targetUserList) {
            String configSection = "default";
            if (this.plugin.getConfig().get("menu.items." + targetUser.getRankName()) != null) {
                configSection = targetUser.getRankName();
            }

            ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
            skullMeta.setOwner(targetUser.getUsername());
            skullMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.plugin.getConfig().getString("menu.items." + configSection + ".display").replace("{username}", targetUser.getUsername())));

            List<String> itemLore = new ArrayList<>();

            for (String loreItem : this.plugin.getConfig().getStringList("menu.items." + configSection + ".lore")) {
                loreItem = loreItem.replace("{username}", targetUser.getUsername());
                loreItem = loreItem.replace("{rank}", targetUser.getRankName());

                itemLore.add(ChatColor.translateAlternateColorCodes('&', loreItem));
            }
            skullMeta.setLore(itemLore);

            itemStack.setItemMeta(skullMeta);
            this.inventory.addItem(itemStack);
        }
    }


    /**
     * Hooks into permissions plugin and searches all users for their group/rank
     * and adds them to the inventory if they are in one of the staff related
     * groups. This also builds the menu right after.
     */
    public void updateMenu() {
        // Clear the list of users so we can load our new users
        this.targetUserList.clear();
        // Do basic profiling so the we/whoever runs this plugin can see how long it takes for it to update the players
        long startTime = System.currentTimeMillis();
        this.plugin.getPermissionsHook().updatePlayers();
        this.plugin.getLogger().info("It took " + (System.currentTimeMillis() - startTime) + "ms to update the players for the " + this.plugin.getPermissionsHook().getPluginName() + " hook");
        // Generate the menu contents
        buildMenu();
    }


    /**
     * Get the inventory which contains all the staff members
     *
     * @return Inventory/menu which contains all the staff members heads
     */
    public Inventory getInventory() {
        return this.inventory;
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

    /**
     * Returns the list of players who are staff members
     *
     * @return Returns a list of {@link TargetUser}
     */
    public List<TargetUser> getTargetUserList() {
        return this.targetUserList;
    }
}
