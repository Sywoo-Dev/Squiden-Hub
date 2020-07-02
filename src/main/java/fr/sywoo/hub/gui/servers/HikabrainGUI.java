package fr.sywoo.hub.gui.servers;

import org.bukkit.Material;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.utils.PlayerUtils;

public class HikabrainGUI extends IQuickInventory{


	public HikabrainGUI() {
		super("§eHikabrain", 9);
	}

	@Override
	public void contents(QuickInventory inv) {
		inv.setItem(new QuickItem(Material.SANDSTONE).setName("§e§l1V1").setLore("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer("Hikabrain")).toItemStack(), onClick -> {
			LionSpigot.get().addPlayerInWaitingQueue(onClick.getPlayer().getPlayer(), "Hikabrain");
			onClick.getPlayer().closeInventory();	
		}, 2);		

		inv.setItem(new QuickItem(Material.RED_SANDSTONE).setLore("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer("HikaTO2")).setName("§e§l2V2").toItemStack(), onClick -> {
			LionSpigot.get().addPlayerInWaitingQueue(onClick.getPlayer().getPlayer(), "HikaTO2");
			onClick.getPlayer().closeInventory();	
		}, 4);	

		inv.setItem(new QuickItem(Material.SAND).setName("§e§l4V4").setLore("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer("HikaTO4")).toItemStack(), onClick -> {
			LionSpigot.get().addPlayerInWaitingQueue(onClick.getPlayer().getPlayer(), "HikaTO4");
			onClick.getPlayer().closeInventory();		
		}, 6);	
	}

}
