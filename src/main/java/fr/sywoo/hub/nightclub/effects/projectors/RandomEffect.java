package fr.sywoo.hub.nightclub.effects.projectors;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;

public class RandomEffect extends ProjectorEffect {

	public BukkitTask task;
	public NightClub nightClub;
	
	public RandomEffect(NightClub nightClub) {
		super("Random", nightClub.getProjectors());
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		nightClub.enableEffect(this);
		task = new BukkitRunnable() {

			@Override
			public void run() {
				for(Projector projs : getProjectors()) {
					projs.rotate(getPiste().getRandomLocation());
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

	@Override
	public void stopSilent() {
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

}
