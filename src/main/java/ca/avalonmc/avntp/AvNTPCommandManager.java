package ca.avalonmc.avntp;

import org.bukkit.command.PluginCommand;

import java.util.HashMap;

import static ca.avalonmc.avntp.AvNTP.log;


class AvNTPCommandManager {
	
	private static HashMap<String, AvNTPCommand> commands = new HashMap<>();
	
	private AvNTPTabCompleter tabCompleter;
	
	
	AvNTPCommandManager (AvNTPTabCompleter tabCompleter) {
		
		this.tabCompleter = tabCompleter;
		
	}
	
	
	@SuppressWarnings ("unchecked")
	void registerCommand (String commandLabel, Class commandClass, String... args) {
		
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
		command.setTabCompleter(tabCompleter);
		
	}
	
	
	static HashMap<String, AvNTPCommand> getCommands () {
		
		return commands;
		
	}
	
}
