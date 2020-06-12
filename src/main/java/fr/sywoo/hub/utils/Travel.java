package fr.sywoo.hub.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Travel {

	public static void travel(final Player player, Plugin plugin, List<Location> locations, int timeInTicks) {
		List<Double> diffs = new ArrayList<>();
		List<Integer> travelTimes = new ArrayList<>();
		double totalDiff = 0.0D;
		for (int i = 0; i < locations.size() - 1; i++) {
			Location s = locations.get(i);
			Location location1 = locations.get(i + 1);
			double diff = positionDifference(s, location1);
			totalDiff += diff;
			diffs.add(Double.valueOf(diff));
		}
		for (Iterator<Double> n = diffs.iterator(); n.hasNext();) {
			double d = ((Double) n.next()).doubleValue();
			travelTimes.add(Integer.valueOf((int) (d / totalDiff * timeInTicks)));
		}
		final List<Location> tps = new ArrayList<>();
		World w = player.getWorld();
		for (int j = 0; j < locations.size() - 1; j++) {
			Location s = locations.get(j);
			Location location1 = locations.get(j + 1);
			int t = ((Integer) travelTimes.get(j)).intValue();
			double moveX = location1.getX() - s.getX();
			double moveY = location1.getY() - s.getY();
			double moveZ = location1.getZ() - s.getZ();
			double movePitch = (location1.getPitch() - s.getPitch());
			double yawDiff = Math.abs(location1.getYaw() - s.getYaw());
			double c = 0.0D;
			if (yawDiff <= 180.0D) {
				if (s.getYaw() < location1.getYaw()) {
					c = yawDiff;
				} else {
					c = -yawDiff;
				}
			} else if (s.getYaw() < location1.getYaw()) {
				c = -(360.0D - yawDiff);
			} else {
				c = 360.0D - yawDiff;
			}
			double d = c / t;
			for (int x = 0; x < t; x++) {
				Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x,
						s.getZ() + moveZ / t * x, (float) (s.getYaw() + d * x),
						(float) (s.getPitch() + movePitch / t * x));
				tps.add(l);
			}
		}
		try {
			final boolean hadFlight = player.getAllowFlight();
			final boolean wasFlying = player.isFlying();
			player.setAllowFlight(true);
			player.teleport(tps.get(0));
			player.setFlying(true);
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				private int ticks = 0;

				public void run() {
					if (this.ticks < tps.size()) {
						player.teleport(tps.get(this.ticks));
						this.ticks++;
					} else {
						player.setFlying(wasFlying);
						player.setAllowFlight(hadFlight);
					}
				}
			}, 1L);
		} catch (Exception e) {}
	}

	public static double positionDifference(Location cLoc, Location eLoc) {
		double cX = cLoc.getX();
		double cY = cLoc.getY();
		double cZ = cLoc.getZ();
		double eX = eLoc.getX();
		double eY = eLoc.getY();
		double eZ = eLoc.getZ();
		double dX = eX - cX;
		if (dX < 0.0D)
			dX = -dX;
		double dZ = eZ - cZ;
		if (dZ < 0.0D)
			dZ = -dZ;
		double dXZ = Math.hypot(dX, dZ);
		double dY = eY - cY;
		if (dY < 0.0D)
			dY = -dY;
		double dXYZ = Math.hypot(dXZ, dY);
		return dXYZ;
	}

}
