package fr.sywoo.hub.animas;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.sywoo.api.spigot.LionSpigot;

public class AnimArenaKit extends Animatronic {

	public AnimArenaKit() {
		super(new Location(Bukkit.getWorld("world"), 58.5, 67.0, -68.5, 0, 0), "ArenaKit", ChatColor.GOLD, ChatColor.YELLOW, 20);
		setWalking(true);
		setStuff(new ItemStack(Material.LEATHER_HELMET), new ItemStack(Material.LEATHER_CHESTPLATE),
				new ItemStack(Material.LEATHER_LEGGINGS), new ItemStack(Material.LEATHER_BOOTS), new ItemStack(Material.DIAMOND_SWORD));
		setHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE1MzlkZGRmOWVkMjU1ZWNlNjM0ODE5M2NkNzUwMTJjODJjOTNhZWMzODFmMDU1NzJjZWNmNzM3OTcxMWIzYiJ9fX0=");
		spawnAndPlay();
	}

	@Override
	public void executeAction() {}

	@SuppressWarnings("deprecation")
	@Override
	public void onClick(PlayerInteractAtEntityEvent event) {
		Player player = event.getPlayer();
		player.sendMessage("§a§lRecherche d'un serveur.");
		for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
			if(snapshot.getServiceId().getName().startsWith("ArenaKit-")){
				if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
				if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
					player.sendMessage("§a§lTéléporation sur " + snapshot.getServiceId().getName());
					LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, snapshot);
					break;
				}
			}
		}
	}
}
