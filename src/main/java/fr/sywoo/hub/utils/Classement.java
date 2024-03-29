package fr.sywoo.hub.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public class Classement {

	private Location loc;
	private Holograms holograms;
	private List<String> leTriSelectif = new ArrayList<>();
	private String title = "";

	public Classement(Location loc, String title, HashMap<String, Integer> map) {
		this.loc = loc;
		this.title = title;
		Map<String, Integer> tri = sort(map);
		leTriSelectif.add(title); leTriSelectif.add(" ");
		int i = 0;
		for(Entry<String, Integer> entry : tri.entrySet()) {
			if(entry.getValue() == -1) continue;
			i++;
			if(i > 10) {
				break;
			}
			leTriSelectif.add(ChatColor.BLUE + entry.getKey() + " §fa réussi le jump en  §c" + new FormatTime(entry.getValue()));
			leTriSelectif.add(" ");
		}
		holograms = new Holograms(loc, leTriSelectif);
	}

	public void update(HashMap<String, Integer> map) {
		Map<String, Integer> tri = sort(map);
		leTriSelectif.clear();
		leTriSelectif.add(title); leTriSelectif.add(" ");
		int i = 0;
		for(Entry<String, Integer> entry : tri.entrySet()) {
			if(entry.getValue() == -1) continue;
			i++;
			if(i > 10) {
				break;
			}
			leTriSelectif.add(ChatColor.BLUE + entry.getKey() + " §fa réussi le jump en  §c" + new FormatTime(entry.getValue()));
			leTriSelectif.add(" ");
		}
		holograms = new Holograms(loc, leTriSelectif);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private HashMap<String, Integer> sort(HashMap map) {
		List linkedlist = new LinkedList(map.entrySet());
		Collections.sort(linkedlist, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = linkedlist.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public Holograms getHolograms() {
		return holograms;
	}

}