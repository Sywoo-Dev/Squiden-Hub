package fr.sywoo.hub.nightclub.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutGameStateChange;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class SkyEffect extends Effect {

	public boolean isSky = false;
	public NightClub nightClub;
	public BukkitTask task;
	public Cuboid sky = new Cuboid(new Location(Bukkit.getWorld("world"), 50, 46, 19),
			new Location(Bukkit.getWorld("world"), 19, 33, 57));
	
	public SkyEffect(NightClub nightClub) {
		super("Clignotement", null);
		this.nightClub = nightClub;
	}

	@Override
	public void start() {
		this.nightClub.enableEffect(this);
		task = new BukkitRunnable() {

			@Override
			public void run() {
				if(isSky) {
					setSky(0.0f);
				}else {
					setSky(0.2f);
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

	public void setSky(float sky) {
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(this.sky.contains(players)) {
				setSky(players, sky);
			}
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void setSky(Player paramPlayer, float sky) {
		PacketPlayOutGameStateChange packetPlayOutGameStateChange1 = new PacketPlayOutGameStateChange(7, sky);
		PacketPlayOutGameStateChange packetPlayOutGameStateChange2 = new PacketPlayOutGameStateChange(8, 80.0F);
		PlayerConnection playerConnection = (((CraftPlayer) paramPlayer).getHandle()).playerConnection;
		playerConnection.sendPacket((Packet) packetPlayOutGameStateChange1);
		playerConnection.sendPacket((Packet) packetPlayOutGameStateChange2);
	}

	@Override
	public void stopSilent() {
		Bukkit.getScheduler().cancelTask(task.getTaskId());
		task.cancel();
	}

}
