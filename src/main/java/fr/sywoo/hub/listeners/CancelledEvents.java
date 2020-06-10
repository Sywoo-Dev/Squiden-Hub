package fr.sywoo.hub.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import fr.sywoo.hub.Hub;

public class CancelledEvents implements Listener {

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		e.setCancelled(true);
	}

//	@EventHandler
//	public void onBreak(BlockBreakEvent e) {
//		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
//			e.setCancelled(true);
//		}
//	}
//	
	@EventHandler
	public void onSuccess(PlayerAchievementAwardedEvent event) {
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onUnload(ChunkUnloadEvent event) {
		event.setCancelled(true);
		event.getChunk().load();
	}

	@EventHandler
	public void onBreakItemFrame(HangingBreakByEntityEvent event) {
		event.setCancelled(true);
	}

//	@EventHandler
//	public void onPlace(BlockPlaceEvent e) {
//		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
//			e.setCancelled(true);
//		}
//	}

	@EventHandler
	public void onInteract(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player))
			return;
		if (((Player) event.getEntity()).getGameMode() != GameMode.CREATIVE) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerArmorStandManipulateEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onInteract(PlayerInteractEntityEvent e) {
		if (e.getPlayer().getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if (!(e.getWhoClicked() instanceof Player))
			return;
		Player player = (Player) e.getWhoClicked();
		if (player.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getCause() == DamageCause.FALL) {
				Player player = (Player) e.getEntity();
				Hub.instance.jumps.put(player.getUniqueId(), 0);
				e.setCancelled(true);
			}
		}
		if (e.getEntity() instanceof ArmorStand)
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void onSpawn(CreatureSpawnEvent e) {
		if (e.getEntity() != null) {
			if (!Hub.instance.customs.contains(e.getEntity().getEntityId())) {
				if (e.getSpawnReason() != SpawnReason.NATURAL)
					return;
				if (e.getEntity() instanceof ArmorStand)
					return;
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	@EventHandler
	public void onXPLevelChance(PlayerExpChangeEvent e) {
		e.setAmount(0);
	}

}
