package fr.sywoo.hub.classements;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.arena.ArenaData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.Location;
import fr.sywoo.hub.utils.Display;

public class ArenaKitClassement extends Display{

	public ArenaKitClassement() {
		super("§b[Classic] §c§lKills", new Location(48, 68, -57).getAsLocation(), 10);
		display();
	}
	
	@Override
	public void display() {
		TreeMap<UUID, Integer> classement = new TreeMap<UUID, Integer>();
		for(AccountData data : LionSpigot.get().getAccountManager().getAllPlayers()) {
			ArenaData arena = LionSpigot.get().getArenaManager().get(data.getUUID());
			if(arena == null) continue; 
			classement.put(data.getUUID(), (int) arena.getKill());
		}
		
		classement.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			String displayName = LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName();
			this.spawnArmorStand("§f" + displayName + " : §c" + action.getValue() + " §bKills");
		});
		
	}
}
