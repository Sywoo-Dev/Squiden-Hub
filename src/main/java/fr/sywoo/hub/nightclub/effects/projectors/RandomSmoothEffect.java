package fr.sywoo.hub.nightclub.effects.projectors;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.nightclub.effects.Effect;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class RandomSmoothEffect extends ProjectorEffect {
	
	public BukkitTask task;
	public NightClub nightClub;
	
	public RandomSmoothEffect(NightClub nightClub) {
		super("Random Smooth", nightClub.getProjectors());
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		nightClub.enableEffect(this);
		task = new BukkitRunnable() {

			@Override
			public void run() {
				for(Projector projs : getProjectors()) {
					projs.travel(getPiste().getRandomLocation(),20);
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
