package fr.sywoo.hub.nightclub.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class ParticleEffect extends Effect{

	public BukkitTask task;
	public NightClub nightClub;
	
	public ParticleEffect(NightClub nightClub, EnumParticle particle) {
		super("Particle", particle);
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {
			
			@Override
			public void run() {
				playEffect(getParticle(), getPiste().getRandomLocation());
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
