package me.sisko.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sisko.phpbb.AccountManager;
import net.md_5.bungee.api.ChatColor;

public class CommandForum implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (p.hasPermission("forums.admin")) {
				if (args.length == 1) {
					if (args[0].equals("reload")) {
						AccountManager.refreshConfig();
						p.sendMessage("Config reloaded");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /forum <reload|checkaccount>");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Insufficient Permissions");
			}
		} else {
			Bukkit.getServer().getLogger().info("You can't do that from console!");
		}
		return true;
	}
	
}
