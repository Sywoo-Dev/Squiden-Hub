package fr.sywoo.hub.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.hub.Hub;

public class PlayerSneak implements Listener {
	
	Hub main;
	
	public PlayerSneak(Hub main) {
		this.main = main;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		if(player.getLocation().getBlockX() == main.carpet.getBlockX() && player.getLocation().getBlockZ() == main.carpet.getBlockZ()) {
			if(player.getLocation().add(0, -2, 0).getBlock().getType() == Material.LADDER && player.isSneaking()) {
				player.sendBlockChange(main.carpet, Material.AIR, (byte)0);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						player.sendBlockChange(main.carpet, Material.CARPET, (byte)0);
					}
				}.runTaskLater(main, 20*5);
			}
		}else if(player.getLocation().getBlockX() == main.slab.getBlockX() && player.getLocation().getBlockZ() == main.slab.getBlockZ()) {
			if(player.getLocation().add(0, -2, 0).getBlock().getType() == Material.AIR && player.isSneaking()) {
				player.sendBlockChange(main.slab, Material.AIR, (byte)0);
				new BukkitRunnable() {
					
					@Override
					public void run() {
						player.sendBlockChange(main.slab, Material.STEP, (byte)3);
					}
				}.runTaskLater(main, 20*5);
			}
		}
	}
	
}
