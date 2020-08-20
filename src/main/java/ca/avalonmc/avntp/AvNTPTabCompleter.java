package ca.avalonmc.avntp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class AvNTPTabCompleter implements TabCompleter {
	
	@Override
	public List<String> onTabComplete (CommandSender sender, Command command, String alias, String[] args) {
		
		List<String> completions = new ArrayList<>();
		AvNTPCommand avnCommand = AvNTPCommandManager.getCommands().get(command.getLabel());
		
		if (args.length - 1 >= avnCommand.getArguments().size()) {
			
			return completions;
			
		}
		
		String argument = avnCommand.getArguments().get(args.length - 1);
		
		if (argument.substring(1, argument.length() - 1).equalsIgnoreCase("player")) {
			
			for (Player p : AvNTP.plugin.getServer().getOnlinePlayers()) {
				
				if (p.getName().startsWith(args[args.length - 1]) && !p.getUniqueId().equals(((Player)sender).getUniqueId())) {
					
					completions.add(p.getName());
					
				}
				
			}
			
		}
		
		return completions;
		
	}
	
}
