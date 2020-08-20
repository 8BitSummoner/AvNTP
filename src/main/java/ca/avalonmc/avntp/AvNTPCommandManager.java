package ca.avalonmc.avntp;

import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import static ca.avalonmc.avntp.AvNTP.log;


public class AvNTPCommandManager {
	
	private static HashMap<String, AvNTPCommand> commands = new HashMap<>();
	
	
	public void registerCommand (String commandLabel, Class commandClass, String... args) {
		
		PluginCommand command = AvNTP.plugin.getCommand(commandLabel);
		
		if (command == null) {
			log.warning("Invalid command: " + commandLabel);
			return;
		}
		
		try {
			
			commands.put(commandLabel, (AvNTPCommand)commandClass.getDeclaredConstructor(PluginCommand.class, String[].class).newInstance(command, args));
			
		} catch (Exception e) {
			
			log.warning("Could not register command: " + commandLabel + "\n" + e.toString());
			e.printStackTrace();
			return;
			
		}
		
		log.info("Registered command: " + commandLabel);
		command.setExecutor(commands.get(commandLabel));
		
	}
	
	
	public static ArrayList<AvNTPCommand> getCommands () {
		
		LinkedHashSet<AvNTPCommand> commandList = new LinkedHashSet<>(commands.values());
		
		return new ArrayList<AvNTPCommand>(commandList);
		
	}
	
}
