package fr.sywoo.hub.customes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Snowball;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.sywoo.hub.utils.CustomEntity;
import fr.sywoo.hub.utils.MathsUtils;

public class HubSnowMan extends CustomEntity{

	public HubSnowMan() {
		super(EntityType.SNOWMAN, "Katapulte", new Location(Bukkit.getWorld("world"), 81.5, 62, -32.5, 45, 0), false, false);
	}
	
	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		event.setCancelled(true);
		Snowball ball = (Snowball) getEntity().getWorld().spawnEntity(getEntity().getLocation(), EntityType.SNOWBALL);
		ball.setVelocity(new MathsUtils().projectTo(ball.getLocation(), event.getPlayer().getLocation()));
		
		event.getPlayer().setVelocity(new MathsUtils().projectTo(event.getPlayer().getLocation(), new Location(Bukkit.getWorld("world"), 0, 72, 2)));
	}
	
}
