package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.hub.gui.servers.uhcs.TaupeGUI;

public class AnimTaupe extends Animatronic {

	public AnimTaupe() {
		super(new Location(Bukkit.getWorld("world"), -3.5, 67.0, 66.5, 180, 0), "Taupe-Gun", ChatColor.DARK_RED, ChatColor.RED, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmVhM2Y0NDc0NTVjZDIyOWRiYzdlYjM5NzRlM2NiNTc2NmFhY2ZjNDZiNWEzYmY0YzU5NmQxOTU5NDE3OWQifX19");
		spawnAndPlay();
	}
	
	@Override
	public void executeAction() {}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		new TaupeGUI().open(event.getPlayer());
	}

}
