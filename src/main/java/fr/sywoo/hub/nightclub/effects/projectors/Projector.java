package fr.sywoo.hub.nightclub.effects.projectors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.utils.SkullProfil;
import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

public class Projector {

	protected int entityId;
	protected DataWatcher dataWatcher;

	public LivingEntity target;
	private Location location;
	private ArmorStand head;

	public Projector(Location location) {
		ArmorStand stand = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
		stand.setGravity(false);
		stand.setMarker(true);
		stand.setVisible(false);
		this.target = (LivingEntity) stand;
		entityId = new Random().nextInt(Integer.MAX_VALUE);
		dataWatcher = new DataWatcher(null);
		byte data = (byte) calcData(0, 0, false); // onFire
		data = (byte) calcData(data, 1, false); // Crouched
		data = (byte) calcData(data, 3, false); // Sprinting
		data = (byte) calcData(data, 4, false); // Eating/Drinking/Block
		data = (byte) calcData(data, 5, true); // Invisible
		dataWatcher.a(0, data);
		dataWatcher.a(6, 20F);
		int type = calcType(0, 2, false); // Is Elderly
		type = calcType(type, 4, false); // Is retracting spikes
		dataWatcher.a(16, type);
		this.location = location;

		Location asLoc = location.clone().add(0, -1.5, 0);

		head = (ArmorStand) asLoc.getWorld().spawnEntity(asLoc, EntityType.ARMOR_STAND);
		head.setMarker(true);
		head.setHelmet(new SkullProfil(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFmMzJjNjRiMTM4OTM3ZmNkNmMzMDNkNGFmYTg1ZGQ5YWQ2ZDgxZTQ0MzUyMjc0MWYzYmRlNzY3NGI2YjkyOCJ9fX0=")
						.applyTextures(
								new QuickItem(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal()).toItemStack()));
		head.setVisible(false);
		head.setGravity(false);

		updateHead();
	}

	public void updateHead() {
		Location lookDir = target.getLocation().subtract(head.getLocation().add(0, 0.05, 0));
		EulerAngle poseAngle = directionToEuler(lookDir);
		head.setHeadPose(poseAngle);
	}

	private EulerAngle directionToEuler(Location dir) {
		double xzLength = Math.sqrt(dir.getX() * dir.getX() + dir.getZ() * dir.getZ());
		double pitch = Math.atan2(xzLength, dir.getY()) - Math.PI / 2;
		double yaw = (-Math.atan2(dir.getX(), dir.getZ()) + Math.PI / 4) - 0.7;
		return new EulerAngle(pitch, yaw, 0);
	}

	@SuppressWarnings("rawtypes")
	private void sendPacket(Packet packet) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

	@SuppressWarnings("rawtypes")
	private void sendPacket(Player player, Packet packet) {
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

	private void spawn(Player show) {
		try {
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving();
			set(packet, "a", entityId);
			set(packet, "b", 68);
			set(packet, "c", toFixedPointNumber(location.getX()));
			set(packet, "d", toFixedPointNumber(location.getY()));
			set(packet, "e", toFixedPointNumber(location.getZ()));
			set(packet, "f", (int) toPackedByte(location.getYaw()));
			set(packet, "g", (int) toPackedByte(location.getPitch()));
			set(packet, "h", (int) toPackedByte(location.getPitch()));
			set(packet, "i", (byte) 0);
			set(packet, "j", (byte) 0);
			set(packet, "k", (byte) 0);
			set(packet, "l", dataWatcher);
			if (show == null) {
				sendPacket(packet);
			} else {
				sendPacket(show, packet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void rotate(Location loc) {
		target.teleport(loc);
		setTarget(target);
	}
	
	public void reset() {
		despawn(null);
		target.teleport(this.location);
		updateHead();
	}
	
	public void setTarget(LivingEntity target) {
		try {
			this.target = target;
			updateHead();
			despawn(null);
			spawn(null);
			PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();
			set(packet, "a", entityId);
			try {
				dataWatcher.a(17, (int) ((CraftEntity) target).getHandle().getId());
			} catch (Exception e) {}
			set(packet, "b", dataWatcher.b());
			sendPacket(packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setTarget(Player show, LivingEntity target) {
		try {
			this.target = target;
			updateHead();
			despawn(show);
			spawn(show);
			PacketPlayOutEntityMetadata packet = new PacketPlayOutEntityMetadata();
			set(packet, "a", entityId);
			try {
				dataWatcher.a(17, (int) ((CraftEntity) target).getHandle().getId());
			} catch (Exception e) {}
			set(packet, "b", dataWatcher.b());
			sendPacket(show, packet);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void despawn(Player show) {
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(new int[] { entityId });
		if (show == null) {
			sendPacket(packet);
		} else {
			sendPacket(show, packet);
		}
	}

	protected int calcData(int data, int id, boolean flag) {
		if (flag) {
			return Integer.valueOf(data | 1 << id);
		} else {
			return Integer.valueOf(data & ~(1 << id));
		}
	}

	protected int calcType(int type, int id, boolean flag) {
		if (flag) {
			return Integer.valueOf(type | id);
		} else {
			return Integer.valueOf(type & ~id);
		}
	}

	protected byte toPackedByte(float f) {
		return (byte) ((int) (f * 256.0F / 360.0F));
	}

	protected int toFixedPointNumber(Double d) {
		return (int) (d * 32D);
	}

	protected void set(Object instance, String name, Object value) throws Exception {
		Field field = instance.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(instance, value);
	}
	
	public void travel(Location location, int timeInTicks) {
		List<Double> diffs = new ArrayList<>();
		double totalDiff = positionDifference(getTarget().getLocation(), location);
		int travelTimes = (int) (totalDiff / totalDiff * timeInTicks);
		final List<Location> tps = new ArrayList<>();
		World w = Bukkit.getWorld("world");
		Location s = getTarget().getLocation().clone();
		Location location1 = location.clone();
		int t = travelTimes;
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
			Location l = new Location(w, s.getX() + moveX / t * x, s.getY() + moveY / t * x, s.getZ() + moveZ / t * x,
					(float) (s.getYaw() + d * x), (float) (s.getPitch() + movePitch / t * x));
			tps.add(l);
		}
		try {
			setTarget(getTarget());
			rotate(tps.get(0));
			new BukkitRunnable() {
				private int ticks = 0;

				@Override
				public void run() {
					if (this.ticks < tps.size()) {
						rotate(tps.get(this.ticks));
						this.ticks++;
					} else {
						this.cancel();
					}
				}
			}.runTaskTimer(Hub.instance, 0, 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public LivingEntity getTarget() {
		return target;
	}
}
