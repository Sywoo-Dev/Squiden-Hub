package fr.sywoo.hub.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteract implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent event) {
		event.setCancelled(true);
		if(event.getClickedBlock() == null) return;
		if(event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(event.getClickedBlock().getType() == Material.CARPET) {
			if(event.getClickedBlock().getLocation().getX() == 37 && event.getClickedBlock().getLocation().getZ() == -19) {
				if(event.getClickedBlock().getData() == 10) {
					Player player = event.getPlayer();
					player.sendBlockChange(event.getClickedBlock().getLocation(), Material.AIR, (byte) 0);
					System.out.println("Changed");
				}
			}
		}
		
		if(event.getClickedBlock().getType() == Material.STEP) {
			if(event.getClickedBlock().getLocation().getX() == -88 && event.getClickedBlock().getLocation().getZ() == 0) {
				if(event.getClickedBlock().getData() == 3) {
					Player player = event.getPlayer();
					player.sendBlockChange(event.getClickedBlock().getLocation(), Material.AIR, (byte) 0);
					System.out.println("changed");
				}
			}
		}
		
	}

}