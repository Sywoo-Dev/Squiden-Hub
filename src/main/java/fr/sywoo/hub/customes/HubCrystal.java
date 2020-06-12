package fr.sywoo.hub.customes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.sywoo.hub.utils.CustomEntity;

public class HubCrystal extends CustomEntity{

	
	public HubCrystal() {
		super(EntityType.ENDER_CRYSTAL, "", new Location(Bukkit.getWorld("world"), -79, 66, 37), false, false);
	}
	
	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		
	}
}
