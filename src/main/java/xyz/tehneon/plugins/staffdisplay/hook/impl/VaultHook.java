package xyz.tehneon.plugins.staffdisplay.hook.impl;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.builder.TargetUser;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

import java.util.List;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public class VaultHook implements PluginHook {

    private StaffDisplay plugin;
    private Permission permissions;

    public VaultHook(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        permissions = plugin.getServer().getServicesManager().getRegistration(Permission.class).getProvider();

        if (permissions == null) {
            plugin.getLogger().severe("Failed to grab Permissions service");
        }
    }

    @Override
    public void updatePlayers() {
        if (permissions == null) {
            plugin.getLogger().severe("Could not update the players as the permissions service could not be found");
            return;
        }

        long startTime = System.currentTimeMillis();

        // Cache the displayable ranks just so we aren't grabbing it constantly when we are looping through all the offline players
        List<String> displayableRanks = plugin.getConfig().getStringList("ranks");

        for (OfflinePlayer offlinePlayer : plugin.getServer().getOfflinePlayers()) {
            //  Grabs all the groups the player is apart of
            String[] usersGroups = permissions.getPlayerGroups(plugin.getServer().getWorlds().get(0).getName(), offlinePlayer);

            for (String findRank : displayableRanks) {
                for (String usersGroup : usersGroups) {
                    if (usersGroup.equalsIgnoreCase(findRank)) {
                        plugin.getMenuBuilder().getTargetUserList().add(new TargetUser(offlinePlayer.getName(), findRank));
                    }
                }
            }
        }

        plugin.getLogger().info("finished, took " + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    public String getPluginName() {
        return "Vault";
    }
}
