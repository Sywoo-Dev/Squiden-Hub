package fr.sywoo.hub.nightclub.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.nightclub.NightClub;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class BoumEffect extends Effect {

	public int rayonMax = 7;
	public int iterateMax = 7;
	public CircleEffect circle;
	public BukkitTask task;
	public NightClub nightClub;
	
	public BoumEffect(NightClub nightClub) {
		super("Boum", EnumParticle.SPELL_WITCH);
		circle = new CircleEffect(nightClub, 0, getCenter(), EnumParticle.SPELL_WITCH);
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {
			@Override
			public void run() {
				for (int i = 0; i < rayonMax; i++) {
					subBoum(circle.getLocation().clone().add(0, 0.5, 0), i);
				}
			}
		}.runTaskTimer(getMain(), 0, 20 * 5);
	}

	public void subBoum(Location loc, int i) {
		new BukkitRunnable() {

			@Override
			public void run() {
				for (Location loc : circle.generateCircle(loc,i)) {
					playEffect(getParticle(), loc);
				}
			}
		}.runTaskLater(Hub.instance, (i + 2));
	}

	@Override
	public void stop() {
		this.nightClub.disableEffect(this);
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

	@Override
	public void stopSilent() {
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

}
