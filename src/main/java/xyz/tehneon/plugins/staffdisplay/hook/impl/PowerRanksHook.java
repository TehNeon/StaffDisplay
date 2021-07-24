package xyz.tehneon.plugins.staffdisplay.hook.impl;

import nl.svenar.powerranks.PowerRanks;
import nl.svenar.powerranks.api.PowerRanksAPI;
import org.bukkit.OfflinePlayer;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.builder.TargetUser;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

import java.util.List;

/**
 * @author TehNeon
 * @since 1/21/2017
 */
public class PowerRanksHook implements PluginHook {

    private StaffDisplay plugin;
    private Permission permissions;

    public PowerRanksHook(StaffDisplay plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init() {
        this.permissions = this.plugin.getServer().getServicesManager().getRegistration(Permission.class).getProvider();

        if (this.permissions == null) {
            this.plugin.getLogger().severe("Failed to grab Permissions service");
        }
    }

    @Override
    public void updatePlayers() {
        if (this.permissions == null) {
            this.plugin.getLogger().severe("Could not update the players as the permissions service could not be found");
            return;
        }

        // Cache the displayable ranks just so we aren't grabbing it constantly when we are looping through all the offline players
        List<String> displayableRanks = this.plugin.getConfig().getStringList("ranks");

        for (OfflinePlayer offlinePlayer : this.plugin.getServer().getOfflinePlayers()) {
            //  Grabs all the groups the player is apart of
            String[] usersGroups = this.permissions.getPlayerGroups(this.plugin.getServer().getWorlds().get(0).getName(), offlinePlayer);

            for (String findRank : displayableRanks) {
                for (String usersGroup : usersGroups) {
                    if (usersGroup.equalsIgnoreCase(findRank)) {
                        this.plugin.getMenuBuilder().getTargetUserList().add(new TargetUser(offlinePlayer.getName(), findRank));
                    }
                }
            }
        }
    }

    @Override
    public String getPluginName() {
        return "PowerRanks";
    }
}
