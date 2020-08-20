package ca.avalonmc.avntp;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;


public abstract class AvNTPCommand implements CommandExecutor {
	
	private String label;
	private String usage;
	private String permission;
	private ArrayList<String> arguments;
	
	protected AvNTP plugin;
	
	
	public AvNTPCommand (PluginCommand commandInfo, String... arguments) {
		
		this.label = commandInfo.getLabel();
		this.usage = commandInfo.getUsage();
		this.permission = commandInfo.getPermission();
		this.arguments = new ArrayList<>(Arrays.asList(arguments));
		
		plugin = AvNTP.plugin;
		
	}
	
	
	public String getLabel () {
		
		return label;
		
	}
	
	
	public String getUsage () {
		
		return usage;
		
	}
	
	
	public String getPermission () {
		
		return permission;
		
	}
	
	
	public ArrayList<String> getArguments () {
		
		return arguments;
		
	}
	
	
	protected boolean canExecute (CommandSender sender, boolean mustBePlayer) {
		
		if (mustBePlayer && !(sender instanceof Player)) {
			
			sender.sendMessage("Sorry, this command is only available to players.");
			return false;
			
		}
		
		if (!sender.hasPermission(permission)) {
			
			sender.sendMessage(AvNTPUtils.processMessage("insufficientPermission"));
			return false;
			
		}
		
		return true;
		
	}
	
	
	protected boolean hasEnoughArgs (CommandSender sender, String[] args) {
		
		if (args.length > arguments.size()) {
			
			sender.sendMessage(AvNTPUtils.processMessage("tooManyArguments"));
			return false;
			
		}
		
		if (args.length < arguments.size()) {
			
			sender.sendMessage(AvNTPUtils.processMessage("tooFewArguments"));
			return false;
			
		}
		
		return true;
		
	}
	
	
	protected boolean isValidPlayerTarget (CommandSender sender, Player targetPlayer) {
		
		if (targetPlayer == null) {
			
			sender.sendMessage(AvNTPUtils.processMessage("playerNotFound"));
			return false;
			
		}
		
		if (!targetPlayer.getWorld().equals(((Player)sender).getWorld())) {
			
			sender.sendMessage(AvNTPUtils.processMessage("playerDifferentDimension"));
			return false;
			
		}
		
		if (targetPlayer.getUniqueId().equals(((Player)sender).getUniqueId())) {
			
			sender.sendMessage(AvNTPUtils.processMessage("targetSameAsSender"));
			return false;
			
		}
		
		return true;
		
	}
	
}
