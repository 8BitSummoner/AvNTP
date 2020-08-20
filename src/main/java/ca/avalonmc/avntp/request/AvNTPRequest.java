package ca.avalonmc.avntp.request;

import ca.avalonmc.avntp.AvNTP;
import ca.avalonmc.avntp.AvNTPUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class AvNTPRequest {
	
	private int countdown;
	private String id;
	
	private RequestType type;
	private Player sender;
	private Player target;
	private double cost;
	
	
	AvNTPRequest (Player sender, Player target, double cost, RequestType type) {
		
		this.type = type;
		this.sender = sender;
		this.target = target;
		this.cost = cost;
		
		this.id = AvNTPUtils.getRequestId(sender, target);
		
		countdown = Bukkit.getScheduler().scheduleSyncDelayedTask(AvNTP.plugin, () -> {
			
			notifyExpiry();
			AvNTPRequestManager.removeRequest(id);
			
		}, 20 * AvNTP.config.getInt("requestLiveTime", 30));
		
		notifyReceived();
		
	}
	
	
	void resolveRequest () {
		
		cancelCountdown();
		notifyAccepted();
		
		if (type == RequestType.TPA) {
			
			sender.teleport(target);
			
		} else if (type == RequestType.TPAHERE) {
			
			target.teleport(sender);
			
		}
		
		AvNTPRequestManager.removeRequest(id);
		
	}
	
	
	void cancelRequest () {
		
		cancelCountdown();
		notifyCancelled();
		AvNTPRequestManager.removeRequest(id);
		
	}
	
	
	void denyRequest () {
		
		cancelCountdown();
		notifyDenied();
		AvNTPRequestManager.removeRequest(id);
		
	}
	
	
	void recalculateCost () {
		
		cost = AvNTPUtils.calculateTravelCost(sender, target);
		
	}
	
	
	double getCost () {
		
		return cost;
		
	}
	
	
	Player getSender () {
		
		return sender;
		
	}
	
	
	private void notifyReceived () {
		
		if (type == RequestType.TPA) {
			
			AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("tpaRequestReceived", "sender", sender.getDisplayName()));
			
		} else if (type == RequestType.TPAHERE) {
			
			AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("tpaHereRequestReceived", "sender", sender.getDisplayName()));
			
		}
		
	}
	
	
	private void notifyAccepted () {
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("outgoingTpRequestAccepted", "target", target.getDisplayName()));
		AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("incomingTpRequestAccepted", "sender", sender.getDisplayName()));
		
	}
	
	
	private void notifyDenied () {
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("outgoingTpRequestDenied", "target", target.getDisplayName()));
		AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("incomingTpRequestDenied", "sender", sender.getDisplayName()));
		
	}
	
	
	private void notifyCancelled () {
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("outgoingTpRequestCancelled", "target", target.getDisplayName()));
		AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("incomingTpRequestCancelled", "sender", sender.getDisplayName()));
		
	}
	
	
	private void notifyExpiry () {
		
		AvNTPUtils.sendMessage(sender, AvNTPUtils.processMessage("outgoingTpRequestTimedOut", "target", target.getDisplayName()));
		AvNTPUtils.sendMessage(target, AvNTPUtils.processMessage("incomingTpRequestTimedOut", "sender", sender.getDisplayName()));
		
	}
	
	
	private void cancelCountdown () {
		
		Bukkit.getScheduler().cancelTask(countdown);
		
	}
	
	
	public enum RequestType {
		TPA, TPAHERE
	}
	
}
