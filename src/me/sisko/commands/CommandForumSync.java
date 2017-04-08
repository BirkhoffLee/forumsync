package me.sisko.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sisko.forums.Core;
import me.sisko.phpbb.RankSync;
import net.md_5.bungee.api.ChatColor;

public class CommandForumSync implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(ChatColor.GREEN + "Syncing your rank with the forums...");
			if (RankSync.sync(p)) {
				p.sendMessage(ChatColor.GREEN + "Done!");
			} else {
				p.sendMessage(ChatColor.RED + "Error updating account (does it exist?)");
			}
		} else {
			Core.plugin.getLogger().info("You can't do that from console!");
		}
		return true;
	}

}
