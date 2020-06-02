package fr.sywoo.hub.customes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.sywoo.hub.utils.CustomEntity;

public class HubChampiMeuh extends CustomEntity {

	public HubChampiMeuh() {
		super(EntityType.MUSHROOM_COW, "ChampiMeuh", new Location(Bukkit.getWorld("world"), -81.5, 62, -32.5, -45, 0), false, true);
	}

	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		event.setCancelled(true);
		event.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20*10, 2));
		event.getPlayer().sendMessage("§cBeurk les champignons !");
	}

}
