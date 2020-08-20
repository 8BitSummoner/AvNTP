package ca.avalonmc.avntp.request;

import ca.avalonmc.avntp.AvNTPUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static ca.avalonmc.avntp.AvNTP.config;
import static ca.avalonmc.avntp.AvNTP.econ;


public class AvNTPRequestManager {
	
	private static HashMap<String, AvNTPRequest> requests = new HashMap<>();
	
	
	public static void registerNewRequest (Player sender, Player targetPlayer, AvNTPRequest.RequestType type) {
		
		if (requests.containsKey(AvNTPUtils.getRequestId(sender, targetPlayer))) {
			
			sender.sendMessage(AvNTPUtils.processMessage("requestAlreadyExists"));
			return;
			
		}
		
		double cost = (int)Math.round(sender.getLocation().distance(targetPlayer.getLocation())) * config.getDouble("costPerBlock", 0);
		
		if (!econ.has(sender, cost)) {
			
			sender.sendMessage(AvNTPUtils.processMessage("notEnoughCurrency", "cost", Double.toString(cost)));
			return;
			
		}
		
		sender.sendMessage(AvNTPUtils.processMessage("requestSent", "targetPlayer", targetPlayer.getDisplayName(), "cost", Double.toString(cost)));
		
		requests.put(AvNTPUtils.getRequestId(sender, targetPlayer), new AvNTPRequest(sender, targetPlayer, type));
		
	}
	
	
	public static void removeRequest (String request) {
		
		requests.remove(request);
	
	}
	
}
