package me.sisko.phpbb;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.sisko.forums.Core;

public class RankSync {
	private static ForumSQL SQL;
	private static FileConfiguration config;

	public static boolean sync(Player p) {
		String group = Core.perms.getPrimaryGroup(p);
		config = Core.plugin.getConfig();
		SQL = new ForumSQL(
				config.getString("sql.ip"),
				config.getString("sql.port"),
				config.getString("sql.database"),
				config.getString("sql.user"),
				config.getString("sql.pass"));
		try {
			String[] id = SQL.querySelect("SELECT * FROM `phpbb_users` WHERE `username`='" + p.getName() + "';", "user_id");
			if(id.length > 0) {
				Core.plugin.getLogger().info(p.getName()+ "'s forum id is " + id[0]);
				int groupNumber = getGroupNumber(group);
				int rankNumber = getRankNumber(group);
				if (getGroupNumber(group) > 0) {
					Core.plugin.getLogger().info("Syncing " + p.getName() + " (group = " + group + ", user id = " + id[0] + ", group id = " + groupNumber + ", rank id = " + rankNumber + ")");
					SQL.queryUpdate("UPDATE `phpbb_users` SET `group_id` = " + groupNumber + " WHERE `user_id` = " + id[0] + ";");
					SQL.queryUpdate("UPDATE `phpbb_users` SET `user_rank` = " + rankNumber + " WHERE `user_id` = " + id[0] + ";");
					SQL.queryUpdate("UPDATE `phpbb_users` SET `user_permissions` = '' WHERE `user_id` = " + id[0] + ";");
					SQL.queryUpdate("UPDATE `phpbb_user_group` SET `group_id`= " + groupNumber + " WHERE `user_id` = " + id[0] + ";");
				} else {
					Core.plugin.getLogger().info(p.getName()+ "'s forum rank was not updated since the player is a guest or user");
				}
			} else {
				Core.plugin.getLogger().info("Could not find player " + p.getName()+ "'s forum id");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static int getGroupNumber(String group) {
		switch (group) {
		case "User": return 2;
		case "User+": return 8;
		case "Donor": return 9;
		case "Donor+": return 10;
		case "Helper": return 11;
		case "Moderator": return 12;
		case "Admin": return 13;
		case "Owner": return 14;
		case "Builder": return 15;
		}
		return -1;
	}
	private static int getRankNumber(String group) {
		switch (group) {
		case "User": return 10;
		case "User+": return 9;
		case "Donor": return 8;
		case "Donor+": return 7;
		case "Helper": return 5;
		case "Moderator": return 4;
		case "Admin": return 3;
		case "Owner": return 2;
		case "Builder": return 6;
		}
		return -1;
	}
}
