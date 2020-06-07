package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.enums.Games;

public class AnimHikaBrain extends Animatronic {

	public AnimHikaBrain() {
		super(new Location(Bukkit.getWorld("world"), 118.5, 67.0, -30.5, 90, 0), "HikaBrain", ChatColor.GOLD, ChatColor.YELLOW, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE1MzlkZGRmOWVkMjU1ZWNlNjM0ODE5M2NkNzUwMTJjODJjOTNhZWMzODFmMDU1NzJjZWNmNzM3OTcxMWIzYiJ9fX0=");
		spawnAndPlay();
	}
	
	@Override
	public void executeAction() {}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		if(!Hub.instance.maintaining.contains(Games.HIKABRAIN)) {
			LionSpigot.get().addPlayerInWaitingQueue(player, Games.HIKABRAIN.getGroup());
		}else {
			player.sendMessage("§cCe jeu est en maintenance !");
		}
	}

}
