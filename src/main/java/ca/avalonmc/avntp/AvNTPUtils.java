package ca.avalonmc.avntp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ca.avalonmc.avntp.AvNTP.messages;


public class AvNTPUtils {
	
	@SuppressWarnings("ConstantConditions")
	public static String processMessage(String key, String... replacements) {
		
		String message = messages.getString(key, "");
		
		message = message.replaceAll("\\{&}", "§");
		
		for (int i = 0; i < replacements.length; i += 2) {
		
			message = message.replaceAll("\\{" + replacements[i] + "}", replacements[i + 1]);
		
		}
		
		return message;
		
	}
	
	
	public static void sendMessage (CommandSender receiver, String message) {
		
		if (message.length() > 0) {
			
			receiver.sendMessage(message);
			
		}
		
	}
	
	
	public static String getRequestId (Player sender, Player targetPlayer) {
		
		return sender.getUniqueId().toString() + "." + targetPlayer.getUniqueId().toString();
		
	}
	
}
