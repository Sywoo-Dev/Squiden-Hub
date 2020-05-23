package fr.sywoo.hub.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import fr.sywoo.api.item.QuickItem;

public class ShopItem extends QuickItem{

	public ShopItem() {
		super(Material.GOLD_INGOT);
		this.setName("§e§lBoutique");
		this.setAction(action -> {
        	Bukkit.dispatchCommand(action.getPlayer(), "store");
        });
	}

}
