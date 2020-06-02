package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.hub.gui.servers.uhcs.LgGUI;

public class AnimLG extends Animatronic {

	public AnimLG() {
		super(new Location(Bukkit.getWorld("world"), 0.5, 67.0, 68.5, 180, 0), "Loup-Garou", ChatColor.DARK_RED, ChatColor.RED, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzY4ZDQzMTI5MzliYjMxMTFmYWUyOGQ2NWQ5YTMxZTc3N2Y4ZjJjOWZjNDI3NTAxY2RhOGZmZTNiMzY3NjU4In19fQ==");
		spawnAndPlay();
	}
	
	@Override
	public void executeAction() {}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		new LgGUI().open(event.getPlayer());
	}

}
