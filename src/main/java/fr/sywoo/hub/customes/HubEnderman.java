package fr.sywoo.hub.customes;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.EntityType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.material.MaterialData;

import fr.sywoo.hub.utils.CustomEntity;

public class HubEnderman extends CustomEntity{

	public HubEnderman() {
		super(EntityType.ENDERMAN, "Enderman", new Location(Bukkit.getWorld("world"), -84.5, 62, 26.5, -90, 0), false, false);
		Enderman enderman = (Enderman) getEntity();
		enderman.setCarriedMaterial(new MaterialData(Material.DIRT));
	}
	
	@Override
	public void clickEvent(PlayerInteractAtEntityEvent event) {
		event.setCancelled(true);
		Enderman enderman = (Enderman) getEntity();
		ArrayList<Material> materials =  new ArrayList<Material>();
		for(Material mats : Material.values()) {
			if(mats.isBlock() && mats.isSolid()) {
				materials.add(mats);
			}
		}
		enderman.setCarriedMaterial(new MaterialData(materials.get(new Random().nextInt(materials.size()))));
	}
	
}
