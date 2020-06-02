package fr.sywoo.hub.classements;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.utils.FormatTime;
import fr.sywoo.hub.utils.Holograms;

public class HikabrainClassements {
	
	TreeMap<UUID, Integer> topPerfect = new TreeMap<UUID, Integer>();
	TreeMap<UUID, Integer> topKills = new TreeMap<UUID, Integer>();
	TreeMap<UUID, Long> topTime = new TreeMap<UUID, Long>();
	TreeMap<UUID, Integer> topWin = new TreeMap<UUID, Integer>();

	public Holograms holoKills;
	public Holograms holoPerfects;
	public Holograms holoTime;
	public Holograms holoWin;
	
	public HikabrainClassements() {
		for(AccountData datas : LionSpigot.get().getAccountManager().getAllPlayers()) {
			if(datas.getBanData() == null) {
				topPerfect.put(datas.getUUID(), datas.getHikabrainStats().getPerfects());
				topKills.put(datas.getUUID(), datas.getHikabrainStats().getKills());
				topTime.put(datas.getUUID(), datas.getHikabrainStats().getTimePlayed());
				topWin.put(datas.getUUID(), datas.getHikabrainStats().getVictory());
			}
		}
		
		List<String> perfects = new ArrayList<String>();
		perfects.add("§cScore parfait §e5-0");
		topPerfect.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			perfects.add(LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName() + " : " + action.getValue() + " Perfects");
		});
		
		List<String> kills = new ArrayList<String>();
		kills.add("§cTop Kills");
		topKills.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			kills.add(LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName() + " : " + action.getValue() + " Kills");
		});
		
		List<String> won = new ArrayList<String>();
		won.add("§cTop Victoires");
		topWin.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			won.add(LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName() + " : " + action.getValue() + " Victoires");
		});
		
		List<String> time = new ArrayList<String>();
		time.add("§cTop Temps");
		topTime.entrySet().stream().sorted(Map.Entry.comparingByValue(Collections.reverseOrder())).limit(10).forEach(action -> {
			time.add(LionSpigot.get().getAccountManager().get(action.getKey()).getDisplayName() + " : " + new FormatTime(action.getValue()).toCutString());
		});
		
		holoKills = new Holograms(new Location(Bukkit.getWorld("world"), 121.5, 68, -36.5), kills);
		holoPerfects = new Holograms(new Location(Bukkit.getWorld("world"), 121.5, 68, -25.5), perfects);
		holoTime = new Holograms(new Location(Bukkit.getWorld("world"), 121.5, 68, -32.5), time);
		holoWin = new Holograms(new Location(Bukkit.getWorld("world"), 121.5, 68, -29.5), won);
		
	}
	
	public void display(Player player) {
		holoKills.display(player);
		holoPerfects.display(player);
		holoTime.display(player);
		holoWin.display(player);
	}

}
