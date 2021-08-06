package xyz.tehneon.plugins.staffdisplay.hook.impl;
package nl.svenar.PowerRanks.addons;

import nl.svenar.powerranks.PowerRanks;
import org.bukkit.OfflinePlayer;
import xyz.tehneon.plugins.staffdisplay.StaffDisplay;
import xyz.tehneon.plugins.staffdisplay.builder.TargetUser;
import xyz.tehneon.plugins.staffdisplay.hook.PluginHook;

import java.util.List;

/**
 * @author TehNeon and Jonfirexbox
 * @since 1/21/2017
 */
import java.util.ArrayList;

import nl.svenar.PowerRanks.Events.ChatTabExecutor;

public abstract class PowerRanksAddon {

	// The author's name
	// ex. return "Your Name";
	public String getAuthor() {
		return "TehNeon";
	}

	// The addon's name
	// ex. return "myAwesomeAddon";
	public String getIdentifier() {
		return "StaffDisplay";
	}

	// The addon's version
	// ex. return "1.0";
	public String getVersion() {
		return "3.0";
	}

	// The minimal requires PowerRanks version
	// ex. return "1.0";
	public String minimalPowerRanksVersion() {
		return "2.0";
	}

	// This function is called once on add-on load
	public void setup() {
	}

	// Called when a player joins the server
	public void onPlayerJoin(PowerRanksPlayer prPlayer) {
	}

	// Called when a player leaves the server
	public void onPlayerLeave(PowerRanksPlayer prPlayer) {
	}

	// Player movement handler
	// Executed when a player has moved
	public void onPlayerMove(PowerRanksPlayer prPlayer) {
	}

	// Called when a player's rank changes
	public void onPlayerRankChange(PowerRanksPlayer prPlayer, String oldRank, String newRank, RankChangeCause cause, boolean isPlayerOnline) {
	}

	// Chat handler
	// The chat message can be altered here
	// has the current chat format as argument, and it must be returned again
	public String onPlayerChat(PowerRanksPlayer prPlayer, String chatFormat, String message) {
		return chatFormat;
	}

	// Command handler
	// Executed when a default PowerRanks command is not found
	// return true after a custom command is handled, otherwise the unknown command
	// message will display, by default it should return false
	public boolean onPowerRanksCommand(PowerRanksPlayer prPlayer, boolean sendAsPlayer, String command, String[] arguments) {
		return false;
	}
	
	// Player world change handler
	// Executed when a player has entered a different world
	public void onPlayerWorldChange(PowerRanksPlayer prPlayer, World world, World world2) {
	}
}
