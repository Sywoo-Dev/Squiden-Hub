package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

public class AnimArena extends Animatronic{
	
	public AnimArena() {
		super(new Location(Bukkit.getWorld("world"), 0.5, 153, 0.5, 75, 0), "Arena", ChatColor.DARK_RED, ChatColor.RED, 20);
		//setWalking(true);
		setSalute(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWQ0NDJkNzIzMWI3OWE2NmI0OWI3NzI0ZjQ0ZjhmNjU4ZWQxNzUzNmZhNGYyNWIzNTNhNDhmNjMwMjc1ZjJmOCJ9fX0=");
		spawnAndPlay();
	}

	@Override
	public void executeAction() {
		
	}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		
	}

}
