package fr.sywoo.hub.nightclub.effects.projectors;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.nightclub.effects.Effect;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public abstract class ProjectorEffect extends Effect{

	public String name;
	public List<Projector> projectors; 
	
	public ProjectorEffect(String name, List<Projector> projectors) {
		super(name, null); 
		this.name = name;
		this.projectors = projectors;
	}

	public abstract void start();

	public abstract void stop();

	public List<Projector> getProjectors() {
		return projectors;
	}
	
	public Hub getMain() {
		return Hub.instance;
	}
}
