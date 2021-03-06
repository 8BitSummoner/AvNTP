package ca.avalonmc.avntp.request;

import ca.avalonmc.avntp.AvNTPUtils;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

import static ca.avalonmc.avntp.AvNTP.econ;


public class AvNTPRequestManager {
	
	private static LinkedHashMap<String, AvNTPRequest> requests = new LinkedHashMap<>();
	
	
	public static boolean registerNewRequest (Player sender, Player targetPlayer, AvNTPRequest.RequestType type) {
		
		if (requests.containsKey(AvNTPUtils.getRequestId(sender, targetPlayer))) {
			
			AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("requestAlreadyExists"));
			return false;
			
		}
		
		double cost = AvNTPUtils.calculateTravelCost(sender, targetPlayer);
		
		if (!econ.has(sender, cost)) {
			
			AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("notEnoughCurrency", "cost", Double.toString(cost)));
			return false;
			
		}
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("requestSent", "targetPlayer", targetPlayer.getDisplayName(), "cost", Double.toString(cost)));
		
		requests.put(AvNTPUtils.getRequestId(sender, targetPlayer), new AvNTPRequest(sender, targetPlayer, cost, type));
		
		return true;
		
	}
	
	
	public static boolean acceptLatestRequest (Player sender) {
		
		ArrayList<String> keys = new ArrayList<>(requests.keySet());
		Collections.reverse(keys);
		
		for (String id : keys) {
			
			if (AvNTPUtils.splitRequestId(id)[1].equalsIgnoreCase(sender.getUniqueId().toString())) {
				
				AvNTPRequest request = requests.get(id);
				
				request.recalculateCost();
				
				econ.withdrawPlayer(request.getSender(), request.getCost());
				AvNTPUtils.sendMessage(request.getSender(), AvNTPUtils.processMessage("tpChargeNotification", "cost", Double.toString(request.getCost())));
				
				request.resolveRequest();
				return true;
				
			}
			
		}
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("noRequestsToAccept"));
		return false;
		
	}
	
	
	public static boolean denyLatestRequest (Player sender) {
		
		ArrayList<String> keys = new ArrayList<>(requests.keySet());
		Collections.reverse(keys);
		
		for (String id : keys) {
			
			if (AvNTPUtils.splitRequestId(id)[1].equalsIgnoreCase(sender.getUniqueId().toString())) {
				
				requests.get(id).denyRequest();
				return true;
				
			}
			
		}
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("noRequestsToDeny"));
		return false;
		
	}
	
	
	public static boolean cancelAllRequests (Player sender) {
		
		ArrayList<String> toCancel = new ArrayList<>();
	
		for (String id : requests.keySet()) {
			
			if (AvNTPUtils.splitRequestId(id)[0].equalsIgnoreCase(sender.getUniqueId().toString())) {
			
				toCancel.add(id);
			
			}
			
		}
		
		if (toCancel.size() == 0) {
			
			AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("noRequestsToCancel"));
			return false;
			
		}
		
		for (String id : toCancel) {
			
			requests.get(id).cancelRequest();
			
		}
		
		return true;
	
	}
	
	
	static void removeRequest (String request) {
		
		requests.remove(request);
	
	}
	
}
