package me.sisko.forums;

import java.io.File;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import me.sisko.commands.*;

public class Core extends JavaPlugin implements Listener {
	public static Core plugin;

	@Override
	public void onEnable() {
		plugin = this;
				
		getCommand("register").setExecutor(new CommandRegister());
		getCommand("forum").setExecutor(new CommandForum());
		getCommand("changepassword").setExecutor(new CommandChangePassword());
		
		getConfig().addDefault("sql.ip", "1.1.1.1");
		getConfig().addDefault("sql.port", "3306");
		getConfig().addDefault("sql.database", "forums");
		getConfig().addDefault("sql.user", "root");
		getConfig().addDefault("sql.pass", "password");
		getConfig().addDefault("sql.user-table", "phpbb_users");
		getConfig().addDefault("sql.columns.names", "usernames");
		getConfig().addDefault("sql.columns.emails", "emails");
		getConfig().addDefault("sql.columns.passwords", "passwords");

		
		if (!getDataFolder().exists()) getDataFolder().mkdirs();
		
		if (!new File(getDataFolder(), "config.yml").exists()) { 
			getLogger().info("Config.yml not found, creating!");
			getConfig().options().copyDefaults(true);
			saveConfig();
		} else {
			getLogger().info("Config.yml found, loading!");
		}
		

		getServer().getPluginManager().registerEvents(this, this);

	}

	@Override
	public void onDisable() {
	}
	
}