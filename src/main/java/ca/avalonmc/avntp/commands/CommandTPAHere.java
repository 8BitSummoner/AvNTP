package ca.avalonmc.avntp.commands;

import ca.avalonmc.avntp.AvNTPCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;


public class CommandTPAHere extends AvNTPCommand {
	
	public CommandTPAHere (PluginCommand commandInfo, String... arguments) {
		
		super(commandInfo, arguments);
		
	}
	
	
	@Override
	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		
		return false;
	}
	
}