package fr.sywoo.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.sywoo.hub.Hub;
import net.minecraft.server.v1_8_R3.NBTTagCompound;

public abstract class CustomEntity implements Listener{

	private Entity entity;
	
	public CustomEntity(EntityType type, String name, Location loc, boolean ai, boolean nameVisibility) {
		entity = loc.getWorld().spawnEntity(loc, type);
		entity.setCustomName(name);
		entity.setCustomNameVisible(nameVisibility);
		
		net.minecraft.server.v1_8_R3.Entity nmsEnt = ((CraftEntity) entity).getHandle();
        NBTTagCompound tag = nmsEnt.getNBTTag();

        if (tag == null) {
            tag = new NBTTagCompound();
        }
        
        if(!ai) {
            nmsEnt.c(tag);
        	tag.setInt("NoAI", 1);
        	tag.setInt("Silent", 1);
            nmsEnt.f(tag);
        }
        Bukkit.getPluginManager().registerEvents(this, Hub.instance);
        Hub.instance.customs.add(entity.getEntityId());
	}
	
	public Entity getEntity() {
		return this.entity;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		if(event.getRightClicked() == null) return;
		if(event.getRightClicked().getCustomName() == null) return;
		if(!event.getRightClicked().getCustomName().equalsIgnoreCase(entity.getCustomName())) return;
		clickEvent(event);
	}
	
	public abstract void clickEvent(PlayerInteractAtEntityEvent event);
	
	public void setInvisible() {
		((CraftEntity) entity).getHandle().setInvisible(true);
	}
	
	public void destroy() {
		entity.teleport(entity.getLocation().add(0, -300, 0));
		entity.remove();
	}
}
