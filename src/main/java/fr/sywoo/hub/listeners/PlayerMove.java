package fr.sywoo.hub.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.utils.Cuboid;
import fr.sywoo.hub.utils.Location;
import fr.sywoo.hub.utils.MathsUtils;

public class PlayerMove implements Listener {

	Cuboid spawn = new Cuboid(new Location(-158, 0, -108).getAsLocation(), new Location(158, 240, 108).getAsLocation());
	Cuboid jump = new Cuboid(new Location(-146, 139, 15).getAsLocation(), new Location(-150, 145, 10).getAsLocation());
	
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		if (player.getGameMode() == GameMode.CREATIVE)
			return;
		if(jump.contains(player)) {
			if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump() == null) {
				player.setVelocity(new MathsUtils().pullEntity(player, new org.bukkit.Location(Bukkit.getWorld("world"), 0, 70, 0), 25));
				player.sendMessage("§cAfin d'entrer, le jump faire tu devras !");
			}else {
				if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump().getSec() < 60) {
					player.setVelocity(new MathsUtils().pullEntity(player, new org.bukkit.Location(Bukkit.getWorld("world"), 0, 70, 0), 25));
					player.sendMessage("§cAfin d'entrer, le jump faire tu devras !");
				}
			}
		}
		if (!spawn.contains(player)) {
			player.teleport(new Location(0.5, 64, 0.5).getAsLocation());
			player.sendMessage("§cHop hop ! Le monde est peut être dangereux !");
		} else {
			if ((player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SEA_LANTERN)
					&& (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
							.getType() == Material.DIAMOND_BLOCK)) {
				if (event.getFrom().distance(event.getTo()) > 0.2) {
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
				if (event.getTo().getY() > event.getFrom().getY()) {
					player.getLocation().setPitch(0);
					player.setVelocity(player.getLocation().getDirection().multiply(4));
					player.setVelocity(new Vector(player.getVelocity().getX(), 1.7D, player.getVelocity().getZ()));
				}
			}
			
			if ((player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SEA_LANTERN)
					&& (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getRelative(BlockFace.DOWN)
							.getType() == Material.GOLD_BLOCK)) {
				if (event.getFrom().distance(event.getTo()) > 0.2) {
					player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
				if (event.getTo().getY() > event.getFrom().getY()) {
					player.getLocation().setPitch(0);
					player.getLocation().setYaw(-90);
					player.setVelocity(player.getLocation().getDirection().multiply(4));
					player.setVelocity(new Vector(player.getVelocity().getX(), 3.5D, player.getVelocity().getZ()));
				}
			}
		}
	}

}
