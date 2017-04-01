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
		
		getCommand("register").setExecutor(new CommandRegister()); // Reister commands
		getCommand("forum").setExecutor(new CommandForum());
		
		getConfig().addDefault("sql.ip", "1.1.1.1"); // add default values to config
		getConfig().addDefault("sql.port", "3306");
		getConfig().addDefault("sql.database", "forums");
		getConfig().addDefault("sql.user", "root");
		getConfig().addDefault("sql.pass", "password");
		getConfig().addDefault("sql.user-table", "phpbb_users");
		getConfig().addDefault("sql.columns.names", "usernames");
		getConfig().addDefault("sql.columns.emails", "emails");
		getConfig().addDefault("sql.columns.passwords", "passwords");

		
		if (!getDataFolder().exists()) getDataFolder().mkdirs(); // make config directory if necessary
		
		if (!new File(getDataFolder(), "config.yml").exists()) { // if the config file doesn't exist
			getLogger().info("Config.yml not found, creating!"); // create the default one
			getConfig().options().copyDefaults(true);
			saveConfig();
		} else {
			getLogger().info("Config.yml found, loading!"); // otherwise do nothing
		}
		

		getServer().getPluginManager().registerEvents(this, this); // allow plugin to listen for spigot events

	}

	@Override
	public void onDisable() {
	}
	
}