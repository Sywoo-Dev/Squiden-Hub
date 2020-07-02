package fr.sywoo.hub.nightclub.effects;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.nightclub.NightClub;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class CylinderEffect extends Effect {

	public int hauteur = 10;
	public CircleEffect circle;
	public List<Location> locs;
	public BukkitTask task;
	public NightClub nightClub;
	
	public CylinderEffect(NightClub nightClub, List<Location> locs) {
		super("Cylindre", EnumParticle.REDSTONE);
		this.circle = new CircleEffect(nightClub,1, null, getParticle());
		this.locs = locs;
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				Location loc = locs.get(new Random().nextInt(locs.size()));
				createCyl(loc, getParticle(), hauteur);
			}
		}.runTaskTimer(getMain(), 0, 20);
	}

	@Override
	public void stop() {
		this.nightClub.disableEffect(this);
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}
	
	public void createCyl(Location loc, EnumParticle particle, int hauteur) {
		double y = 0;
		for (int i = 0; i < hauteur; i++) {
			subCyl(circle.generateCircle(loc.clone().add(0, y, 0),1), particle, i);
			y += 0.3;
		}
	}
	
	public void subCyl(List<Location> locs, EnumParticle particle, int i) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Location loc : locs) {
					playEffect(particle, loc);
				}
			}
		}.runTaskLater(Hub.instance, (i+2));
	}

	@Override
	public void stopSilent() {
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}
}
