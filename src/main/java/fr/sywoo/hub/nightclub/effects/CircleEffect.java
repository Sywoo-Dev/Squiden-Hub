package fr.sywoo.hub.nightclub.effects;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class CircleEffect extends Effect {

	public Location loc;
	public int rayon;
	public BukkitTask task;
	public NightClub nightClub;
	
	public CircleEffect(NightClub nightClub, int rayon, Location loc, EnumParticle particle) {
		super("Circle", particle);
		this.rayon = rayon;
		this.loc = loc;
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Location loc : generateCircle(loc,rayon)) {
					playEffect(particle, loc);
				}
			}
		}.runTaskTimer(getMain(), 0, 20);
	}

	@Override
	public void stop() {
		this.nightClub.disableEffect(this);
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

	public List<Location> generateCircle(Location loc, int rayon) {
		List<Location> list = new ArrayList<>();
		for (double t = 0; t < 10; t += 0.5) {
			double x = rayon * Math.sin(t);
			double z = rayon * Math.cos(t);
			list.add(loc.clone().add(x, 0, z));
		}
		return list;
	}

	public Location getLocation() {
		return loc;
	}

	@Override
	public void stopSilent() {
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

	public void setRayon(int i) {
		rayon = i;
	}
}
