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

public class AnimKaptur extends Animatronic {

	public AnimKaptur() {
		super(new Location(Bukkit.getWorld("world"), 55.5, 67.0, 69.5, 180, 0), "Kaptur", ChatColor.DARK_AQUA, ChatColor.AQUA, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWRiZGFhNzU1MDk5ZWRkN2VmYTFmMTI4ODJjN2E1MWI1ODE1ZGI1MmUwYjE2NGFlZjZkZjlhMWY1M2VjYTIzIn19fQ==");
		spawnAndPlay();
	}
	
	@Override
	public void executeAction() {}

	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		if(!Hub.instance.maintaining.contains(Games.KAPTUR)) {
			if(Queue.getPlayerQueue(player) != null) {
				Queue.getPlayerQueue(player).removePlayer(player);
			}
			if(LionSpigot.get().getServerManager().getServerGroup(Games.KAPTUR.getGroup()).size() == 0) {
				String name = LionSpigot.get().getServerManager().createAndGetServerName(Games.KAPTUR.getGroup());
				LionSpigot.get().getServerDataManager().create(new ServersData(LionSpigot.get().getProjectName(), name, ServerStatus.WAITING, Games.KAPTUR.name()));
				player.sendMessage("§a§lUn Serveur est en cours de lancement...");
			}
			if(!Queue.existFor(Games.KAPTUR.getGroup())) {
				new Queue(Games.KAPTUR.getGroup(), Games.KAPTUR.name(), Games.KAPTUR.getGroup());
			}
			AccountData data = LionSpigot.get().getAccountManager().get(player.getUniqueId());
			Queue.getByName(Games.KAPTUR.getGroup()).addPlayer(data.getRank().getPower() + player.getUniqueId());
		}else {
			player.sendMessage("§cCe jeu est en maintenance !");
		}
	}

}
