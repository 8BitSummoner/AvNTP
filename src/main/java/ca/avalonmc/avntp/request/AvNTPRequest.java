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
	
	
	AvNTPRequest (Player sender, Player target, RequestType type) {
		
		this.type = type;
		this.sender = sender;
		this.target = target;
		
		this.id = AvNTPUtils.getRequestId(sender, target);
		
		countdown = Bukkit.getScheduler().scheduleSyncDelayedTask(AvNTP.plugin, () -> {
			
			notifyExpiry();
			AvNTPRequestManager.removeRequest(id);
			
		}, 20 * AvNTP.config.getInt("requestLiveTime", 30));
		
		if (type == RequestType.TPA) {
		
			notifyTPA();
		
		} else if (type == RequestType.TPAHERE) {
			
			notifyTPAHere();
			
		}
		
	}
	
	
	private void notifyTPA() {
		
		target.sendMessage(AvNTPUtils.processMessage("tpaRequestReceived", "sender", sender.getDisplayName()));
		
	}
	
	
	private void notifyTPAHere() {
		
		target.sendMessage(AvNTPUtils.processMessage("tpaHereRequestReceived", "sender", sender.getDisplayName()));
		
	}
	
	
	private void notifyExpiry() {
		
		sender.sendMessage(AvNTPUtils.processMessage("outgoingTpRequestTimedOut", "target", target.getDisplayName()));
		target.sendMessage(AvNTPUtils.processMessage("incomingTpRequestTimedOut", "sender", sender.getDisplayName()));
		
	}
	
	
	private void cancelCountdown() {
		
		Bukkit.getScheduler().cancelTask(countdown);
		
	}
	
	
	public enum RequestType {
		TPA, TPAHERE;
	}
	
}
