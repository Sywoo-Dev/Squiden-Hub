package fr.sywoo.hub.nightclub.effects;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class DanceFloorEffect extends Effect {

	public BukkitTask task;
	public Cuboid glass;
	public NightClub nightClub;
	
	public DanceFloorEffect(NightClub nightClub) {
		super("DanceFloor", EnumParticle.CLOUD);
		this.nightClub = nightClub;
		this.glass = new Cuboid(Bukkit.getWorld("world"), 37, 34, 46, 27, 33, 35);
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for(Block bloc : glass.getBlocks()) {
					if(bloc.getType() == Material.STAINED_GLASS) {
						bloc.setData((byte)new Random().nextInt(15));
					}
				}
			}
		}.runTaskTimer(getMain(), 0, 5);
	}

	@Override
	public void stop() {
		this.nightClub.disableEffect(this);
		task.cancel();
		Bukkit.getScheduler().cancelTask(task.getTaskId());
	}

	@Override
	public void stopSilent() {
		task.cancel();
		Bukkit.getScheduler().cancelTask(task.getTaskId());
	}

}
