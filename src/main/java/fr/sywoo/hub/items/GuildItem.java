package fr.sywoo.hub.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import fr.sywoo.api.item.QuickItem;

public class GuildItem extends QuickItem{

	public GuildItem() {
		super(Material.BANNER, 1, (byte) 14);
		this.setName("§a§lGuilde");
		this.setAction(action -> {
        	Bukkit.dispatchCommand(action.getPlayer(), "guild");
        });
	}

}
