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

public class AnimUHCRun extends Animatronic {

	public AnimUHCRun() {
		super(new Location(Bukkit.getWorld("world"), -54.5, 67.0, 68.5, 180, 0), "UHCRun", ChatColor.GOLD, ChatColor.YELLOW, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTBkZmM4YTM1NjNiZjk5NmY1YzFiNzRiMGIwMTViMmNjZWIyZDA0Zjk0YmJjZGFmYjIyOTlkOGE1OTc5ZmFjMSJ9fX0=");
		spawnAndPlay();
	}
	
	@Override
	public void executeAction() {}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		if(!Hub.instance.maintaining.contains(Games.UHCRUN)) {
			LionSpigot.get().addPlayerInWaitingQueue(player, Games.UHCRUN.getGroup());
		}else {
			player.sendMessage("§cCe jeu est en maintenance !");
		}
	}

}
