package fr.sywoo.hub.customes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.sywoo.hub.utils.CustomEntity;

public class HubSheep extends CustomEntity {
	
	public HubSheep() {
		super(EntityType.SHEEP, "Moutinou", new Location(Bukkit.getWorld("world"), 76.5, 62, 33.5, 135, 0), false, false);
	}
	
	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		event.setCancelled(true);
		
		event.getRightClicked().setPassenger(event.getPlayer());
		event.getPlayer().sendMessage("§a§lYouhaaaa !");
	}

}
