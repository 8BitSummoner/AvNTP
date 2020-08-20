package ca.avalonmc.avntp;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static ca.avalonmc.avntp.AvNTP.config;
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
	
	
	public static String[] splitRequestId (String id) {
		
		return id.split("\\.");
		
	}
	
	
	public static double calculateTravelCost (Player sender, Player targetPlayer) {
		
		return roundToPlaces(
				(int)Math.round(sender.getLocation().distance(targetPlayer.getLocation())) * config.getDouble("costPerBlock", 0),
				config.getString("costPerBlock", "0").split("\\.")[1].length() + 1);
		
	}
	
	
	public static double roundToPlaces(double value, int places) {
		
		double scale = Math.pow(10, places);
		
		return Math.round(value * scale) / scale;
		
	}
	
}
