package fr.sywoo.hub.customes;

import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.sywoo.hub.utils.CustomEntity;
import fr.sywoo.hub.utils.Location;

public class HubCrystal extends CustomEntity{

	
	public HubCrystal() {
		super(EntityType.ENDER_CRYSTAL, "", new Location(-78.85, 66, 36.8).getAsLocation(), false, false);
	}
	
	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		
	}
}
