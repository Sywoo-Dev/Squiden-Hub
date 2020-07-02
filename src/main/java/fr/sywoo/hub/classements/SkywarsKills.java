package fr.sywoo.hub.classements;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.statistics.SkywarsStats;
import fr.sywoo.api.utils.Location;
import fr.sywoo.hub.utils.Display;

public class SkywarsKills extends Display{

	public SkywarsKills() {
		super("§c§lKills", new Location(-117, 68, 27).getAsLocation(), 10);
		display();
	}
	
	@Override
	public void display() {
		TreeMap<UUID, Integer> classement = new TreeMap<UUID, Integer>();
		for(AccountData data : LionSpigot.get().getAccountManager().getAllPlayers()) {
			SkywarsStats stats = data.getSkywarsStats();
			if(stats == null) continue; 
			classement.put(data.getUUID(), (int) stats.getKills());
		}
		
		classement.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			String displayName = LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName();
			this.spawnArmorStand("§f" + displayName + " : §c" + action.getValue() + " §bKills");
		});
		
	}
}
