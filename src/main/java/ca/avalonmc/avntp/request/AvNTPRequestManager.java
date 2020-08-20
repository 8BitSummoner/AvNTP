package ca.avalonmc.avntp.request;

import ca.avalonmc.avntp.AvNTPUtils;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static ca.avalonmc.avntp.AvNTP.config;
import static ca.avalonmc.avntp.AvNTP.econ;


public class AvNTPRequestManager {
	
	private static HashMap<String, AvNTPRequest> requests = new HashMap<>();
	
	
	public static boolean registerNewRequest (Player sender, Player targetPlayer, AvNTPRequest.RequestType type) {
		
		if (requests.containsKey(AvNTPUtils.getRequestId(sender, targetPlayer))) {
			
			AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("requestAlreadyExists"));
			return false;
			
		}
		
		double cost = AvNTPUtils.roundToPlaces((int)Math.round(sender.getLocation().distance(targetPlayer.getLocation())) * config.getDouble("costPerBlock", 0), 3);
		
		if (!econ.has(sender, cost)) {
			
			AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("notEnoughCurrency", "cost", Double.toString(cost)));
			return false;
			
		}
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("requestSent", "targetPlayer", targetPlayer.getDisplayName(), "cost", Double.toString(cost)));
		
		requests.put(AvNTPUtils.getRequestId(sender, targetPlayer), new AvNTPRequest(sender, targetPlayer, type));
		
		return true;
		
	}
	
	
	public static void removeRequest (String request) {
		
		requests.remove(request);
	
	}
	
}
