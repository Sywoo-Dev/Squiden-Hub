package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.queue.Queue;
import fr.sywoo.api.serverdata.ServerStatus;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.enums.Games;

public class AnimSkyWars extends Animatronic {

	public AnimSkyWars() {
		super(new Location(Bukkit.getWorld("world"), -116.5, 67.0, 31.5, -90, 0), "SkyWars", ChatColor.GOLD, ChatColor.YELLOW, 20);
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
		if(!Hub.instance.maintaining.contains(Games.SKYWARS)) {
			if(Queue.getPlayerQueue(player) != null) {
				Queue.getPlayerQueue(player).removePlayer(player);
			}
			if(LionSpigot.get().getServerManager().getServerGroup(Games.SKYWARS.getGroup()).size() == 0) {
				String name = LionSpigot.get().getServerManager().createAndGetServerName(Games.SKYWARS.getGroup());
				LionSpigot.get().getServerDataManager().create(new ServersData(LionSpigot.get().getProjectName(), name, ServerStatus.WAITING, Games.SKYWARS.name()));
				player.sendMessage("§a§lUn Serveur est en cours de lancement...");
			}
			if(!Queue.existFor(Games.SKYWARS.getGroup())) {
				new Queue(Games.SKYWARS.getGroup(), Games.SKYWARS.name(), Games.SKYWARS.getGroup());
			}
			AccountData data = LionSpigot.get().getAccountManager().get(player.getUniqueId());
			Queue.getByName(Games.SKYWARS.getGroup()).addPlayer(data.getRank().getPower() + player.getUniqueId());
		}else {
			player.sendMessage("§cCe jeu est en maintenance !");
		}
	}

}
