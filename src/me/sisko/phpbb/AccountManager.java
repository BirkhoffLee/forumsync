package me.sisko.phpbb;

import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import me.sisko.forums.Core;

public class AccountManager {
	private static FileConfiguration config = Core.plugin.getConfig();
	private final static String CREATEACCOUNTTEMPLATE = ""
			+ "INSERT INTO `phpbb_users` (`user_id`, `user_type`, `group_id`, `user_permissions`, `user_perm_from`, `user_ip`, `user_regdate`, `username`, `username_clean`, `user_password`, `user_passchg`, `user_email`, `user_email_hash`, `user_birthday`, `user_lastvisit`, `user_lastmark`, `user_lastpost_time`, `user_lastpage`, `user_last_confirm_key`, `user_last_search`, `user_warnings`, `user_last_warning`, `user_login_attempts`, `user_inactive_reason`, `user_inactive_time`, `user_posts`, `user_lang`, `user_timezone`, `user_dateformat`, `user_style`, `user_rank`, `user_colour`, `user_new_privmsg`, `user_unread_privmsg`, `user_last_privmsg`, `user_message_rules`, `user_full_folder`, `user_emailtime`, `user_topic_show_days`, `user_topic_sortby_type`, `user_topic_sortby_dir`, `user_post_show_days`, `user_post_sortby_type`, `user_post_sortby_dir`, `user_notify`, `user_notify_pm`, `user_notify_type`, `user_allow_pm`, `user_allow_viewonline`, `user_allow_viewemail`, `user_allow_massemail`, `user_options`, `user_avatar`, `user_avatar_type`, `user_avatar_width`, `user_avatar_height`, `user_sig`, `user_sig_bbcode_uid`, `user_sig_bbcode_bitfield`, `user_jabber`, `user_actkey`, `user_newpasswd`, `user_form_salt`, `user_new`, `user_reminded`, `user_reminded_time`) "
			+ "VALUES (NULL, '0', '2', '', '0', '%IP%', '%TIME%', '%USERNAME%', '%USERNAME_CLEAN%', '%HASHED_PASSWORD%', '0', '%EMAIL%', '0', '', '0', '0', '0', '', '', '0', '0', '0', '0', '0', '0', '0', 'en', 'America/Anguilla', 'D M d, Y g:i a', '1', '0', '', '0', '0', '0', '0', '-3', '0', '0', 't', 'd', '0', 't', 'a', '0', '1', '0', '1', '1', '1', '1', '230271', '', '', '0', '0', '', '', '', '', '', '', '', '1', '0', '0');";

	private final static String ADDTOGROUPTEMPLATE = "INSERT INTO `phpbb_user_group` (`group_id`, `user_id`, `group_leader`, `user_pending`) VALUES ('2', '%USERID%', '0', '0')";
	
	private final static String CHANGEPASSWORDTEMPLATE = "UPDATE `phpbb_users` SET `user_password` = '%HASHED_PASSWORD%' WHERE `phpbb_users`.`username` = '%USERNAME%';";

	private static ForumSQL SQL = new ForumSQL(
			config.getString("sql.ip"),
			config.getString("sql.port"),
			config.getString("sql.database"),
			config.getString("sql.user"),
			config.getString("sql.pass"));

	public static void refreshConfig() {
		Core.plugin.reloadConfig();
		config = Core.plugin.getConfig();
		SQL = new ForumSQL(
				config.getString("sql.ip"),
				config.getString("sql.port"),
				config.getString("sql.database"),
				config.getString("sql.user"),
				config.getString("sql.pass"));
	}
	public static boolean createAccount(String name, String email, String pass, String ip) {
		try {
			String account = CREATEACCOUNTTEMPLATE;
			account = account.replace("%IP%", ip);
			account = account.replace("%TIME%", Long.toString(System.currentTimeMillis() / 1000L));
			account = account.replace("%USERNAME%", name);
			account = account.replace("%USERNAME_CLEAN%", name.toLowerCase());
			account = account.replace("%HASHED_PASSWORD%", Hasher.hash(pass));
			account = account.replace("%EMAIL%", email);
			SQL.queryUpdate(account);
			String id = SQL.querySelect("SELECT MAX(user_id) AS user_id FROM `phpbb_users`", "user_id")[0];
			String group = ADDTOGROUPTEMPLATE;
			group = group.replace("%USERID%", id);
			System.out.println("Found user ID: " + id);
			SQL.queryUpdate(group);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public static boolean changePassword(String name, String pass) {
		try {
			String password = CHANGEPASSWORDTEMPLATE;
			password = password.replace("%USERNAME%", name);
			password = password.replace("%HASHED_PASSWORD%", Hasher.hash(pass));
			SQL.queryUpdate(password);
		} catch (SQLException e) {
			//e.printStackTrace();
			return false;
		}
		return true;
	}
}
