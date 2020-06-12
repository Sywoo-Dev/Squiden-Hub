package fr.sywoo.hub.effects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import fr.sywoo.hub.task.EffectTask;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class Effect {

	public Map<Laser, ArmorStand> lasers = new HashMap<>();
	public Map<Laser, ArmorStand> lasers2 = new HashMap<>();
	EffectTask task;

	public Effect(EffectTask task) {
		this.task = task;
	}

	public void createCyl(Location loc, EnumParticle particle, long auteur, double rayon) {
		double r = rayon;
		double y = 0;
		for (int i = 0; i < auteur; i++) {
			for (double t = 0; t < 10; t += 0.5) {
				float x = (float) (((float) r) * Math.sin(t));
				float z = (float) (((float) r) * Math.cos(t));
				playEffect(particle, loc.add(x, y, z));
				loc.subtract(x, y, z);
			}
			y += 0.3;
		}
	}

	private void playEffect(EnumParticle particle, Location loc) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true, (float) loc.getX(),
				(float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 3);
		for (Player online : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
		}
	}

	public void initLasers(List<Location> lasers) {
		for (Location loc : lasers) {
			ArmorStand a = loc.getWorld().spawn(generateRandomLocationInPiste(), ArmorStand.class);
			a.setGravity(false);
			a.setVisible(false);
			
			Laser laser = new Laser(loc,a);
			
			
			laser.setTarget(a);
			this.lasers.put(laser,a);
		}
	}
	
	public void initLasers2(List<Location> lasers) {
		for (Location loc : lasers) {
			ArmorStand a = loc.getWorld().spawn(generateRandomLocationInPiste(), ArmorStand.class);
			a.setGravity(false);
			a.setVisible(false);
			
			Laser laser = new Laser(loc,a);
			
			
			laser.setTarget(a);
			this.lasers2.put(laser,a);
		}
	}

	public Location generateRandomLocationInPiste() {
		return task.piste.getRandomLocation();
	}

	public void changeLasersPositions() {
		for (Entry<Laser, ArmorStand> laser : lasers.entrySet()) {
			ArmorStand a = laser.getValue();
			a.teleport(generateRandomLocationInPiste());
			try {
				laser.getKey().setTarget(a);
			} catch (Exception e) {}
		}
	}

	public void changeLasers2Positions() {
		for (Entry<Laser, ArmorStand> laser : lasers2.entrySet()) {
			ArmorStand a = laser.getValue();
			a.teleport(generateRandomLocationInPiste());
			try {
				laser.getKey().setTarget(a);
			} catch (Exception e) {}
		}
	}

}
