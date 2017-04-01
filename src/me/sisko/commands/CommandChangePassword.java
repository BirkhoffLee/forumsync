package me.sisko.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.sisko.forums.PasswordCreator;
import me.sisko.phpbb.AccountManager;
import net.md_5.bungee.api.ChatColor;

public class CommandChangePassword implements CommandExecutor {

	private static ArrayList<PasswordCreator> passwords = new ArrayList<PasswordCreator>();
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			if (args.length == 1) { 
				for (int i = 0; i < passwords.size(); i++) {
					if (passwords.get(i).getName().equals(p.getName())) passwords.remove(i);
				}
				passwords.add(new PasswordCreator(p.getName(), args[0]));
				p.sendMessage(ChatColor.AQUA + "Confirm your password with /changepassword confirm <password>");
			} else if (args.length == 2) {
				if (args[0].equals("confirm")) {
					boolean foundName = false;
					for (int i = 0; i < passwords.size(); i++) {
						if (passwords.get(i).getName().equals(p.getName())) {
							foundName = true;
							if (passwords.get(i).getPass().equals(args[1])) {
								if(AccountManager.changePassword(passwords.get(i).getName(), passwords.get(i).getPass())) {
									p.sendMessage(ChatColor.GREEN + "Password Changed!");
								} else {
									p.sendMessage(ChatColor.RED + "Error changing password (does your account exist?)");
								}
								passwords.remove(i);
								return true;
							}
						}
					}
					if (foundName) {
						p.sendMessage(ChatColor.RED + "Passwords do not match");
					} else {
						p.sendMessage(ChatColor.RED + "You must change your password before confirming it");
					}
				} else {
					p.sendMessage(ChatColor.RED + "Usage: /changepassword <newpassword>");
				}
			} else {
				p.sendMessage(ChatColor.RED + "Usage: /changepassword <newpassword>");
			}
		} else {
			Bukkit.getServer().getLogger().info("You can't do that from console!");
		}
		return true;
	}

}
