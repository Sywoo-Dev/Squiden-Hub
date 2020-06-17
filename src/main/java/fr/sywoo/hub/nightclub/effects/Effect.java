package fr.sywoo.hub.nightclub.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public abstract class Effect {

	public String name;
	public EnumParticle particle;

	public Effect(String name, EnumParticle particle) {
		this.name = name;
		this.particle = particle;
	}

	public abstract void start();

	public abstract void stop();
	public abstract void stopSilent();

	public Location getCenter() {
		return new Location(Bukkit.getWorld("world"), 32.5, 34.0, 40.5);
	}

	public String getName() {
		return name;
	}

	public Hub getMain() {
		return Hub.instance;
	}

	public Cuboid getPiste() {
		return new Cuboid(new Location(Bukkit.getWorld("world"), 38, 38, 46),
				new Location(Bukkit.getWorld("world"), 26, 34, 34));
	}

	public EnumParticle getParticle() {
		return particle;
	}

	@SuppressWarnings("rawtypes")
	public void sendPacket(Packet... packets) {
		for (Player players : Bukkit.getOnlinePlayers()) {
			for (Packet packet : packets) {
				((CraftPlayer) players).getHandle().playerConnection.sendPacket(packet);
			}
		}
	}

	public void playEffect(EnumParticle particle, Location loc) {
		playEffect(particle, 0, 0, 0, loc);
	}

	public void playEffect(EnumParticle particle, float f1, float f2, float f3, Location loc) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) loc.getX(),
				(float) loc.getY(), (float) loc.getZ(), f1, f2, f3, 0, 3);
		for (Player online : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
		}
	}
}
