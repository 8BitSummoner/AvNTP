package ca.avalonmc.avntp.commands;

import ca.avalonmc.avntp.AvNTPCommand;
import ca.avalonmc.avntp.request.AvNTPRequest;
import ca.avalonmc.avntp.request.AvNTPRequestManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;


public class CommandTPA extends AvNTPCommand {
	
	public CommandTPA (PluginCommand commandInfo, String... arguments) {
		
		super(commandInfo, arguments);
		
	}
	
	
	@Override
	public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
		
		if (!canExecute(sender, true)) {
			
			return false;
			
		}
		
		if (!hasEnoughArgs(sender, args)) {
		
			return false;
		
		}
		
		Player targetPlayer = plugin.getServer().getPlayer(args[0]);
		
		if (!isValidPlayerTarget(sender, targetPlayer)) {
		
			return false;
		
		}
		
		return AvNTPRequestManager.registerNewRequest((Player)sender, targetPlayer, AvNTPRequest.RequestType.TPA);
		
	}
	
}