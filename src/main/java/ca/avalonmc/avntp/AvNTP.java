package ca.avalonmc.avntp;

import ca.avalonmc.avntp.commands.CommandTPA;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;


public final class AvNTP extends JavaPlugin {
	
	public static FileConfiguration config;
	public static FileConfiguration messages;
	public static Economy econ;
	public static AvNTP plugin;
	static Logger log;
	
	
	@Override
	public void onEnable () {  //ON PLUGIN ENABLE
		
		registerComponents();
		loadPluginMessages();
		
		log.info("===========================");
		log.info("AvNTP: Plugin Enabled! ");
		log.info("===========================");
		
	}
	
	
	@Override
	public void onDisable () {
		
		log.info("===========================");
		log.info("AvNTP: Plugin Disabled!");
		log.info("===========================");
		
	}
	
	
	private void registerComponents () {
		
		// Instantiate logger
		log = getLogger();
		
		if (!retrieveEconomy()) {
			
			log.severe("Unable to retrieve Economy. Disabling...");
			return;
			
		}
		
		plugin = this;
		
		// Create config file (if needed)
		saveDefaultConfig();
		
		// Get config
		config = this.getConfig();
		
		// Register commands
		AvNTPCommandManager commandManager = new AvNTPCommandManager();
		
		commandManager.registerCommand("avntpa", CommandTPA.class, "<player>");
		
	}
	
	
	private boolean retrieveEconomy() {
		
		if (getServer().getPluginManager().getPlugin("Vault") == null) {
			return false;
		}
		
		RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp == null) {
			return false;
		}
		
		econ = rsp.getProvider();
		
		return true;
		
	}
	
	
	@SuppressWarnings ("ResultOfMethodCallIgnored")
	private void loadPluginMessages() {
	
		File messagesFile = new File(getDataFolder(), "messages.yml");
		
		if (!messagesFile.exists()) {
			
			messagesFile.getParentFile().mkdirs();
			saveResource("messages.yml", false);
			
		}
		
		messages = new YamlConfiguration();
		
		try {
			
			messages.load(messagesFile);
		
		} catch (IOException | InvalidConfigurationException e) {
			
			e.printStackTrace();
			
		}
	
	}
	
}
