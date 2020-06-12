package fr.sywoo.hub.effects;

import java.lang.reflect.Field;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.minecraft.server.v1_8_R3.DataWatcher;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityMetadata;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

@SuppressWarnings("rawtypes")
public class Laser {

	protected int entityId;
	protected DataWatcher dataWatcher;

	private LivingEntity target;
	private Location location;
	private ArmorStand head;

	public Laser(Location location, LivingEntity target) {
		this.target = target;
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
		
		Location asLoc = location.clone().add(0, -1, 0);
		
		head = (ArmorStand) asLoc.getWorld().spawnEntity(asLoc, EntityType.ARMOR_STAND);
		head.setMarker(true);
		head.setVisible(false);
		head.setGravity(false);
		head.setHelmet(null);
		
		Vector to = target.getLocation().toVector().subtract(location.toVector());
		
		head.setHeadPose(new EulerAngle(to.getX(), to.getY(), to.getZ()));
	}
	
	public void updateHead() {
		Vector to = target.getLocation().toVector().subtract(location.toVector());
		head.setHeadPose(new EulerAngle(to.getX(), to.getY(), to.getZ()));
	}

	private void sendPacket(Packet packet) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}

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

	public void setTarget(LivingEntity target) {
		try {
			this.target = target;
			Vector to = target.getLocation().toVector().subtract(location.toVector());
			
			head.setHeadPose(new EulerAngle(to.getX(), to.getY(), to.getZ()));
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
			Vector to = target.getLocation().toVector().subtract(location.toVector());
			
			head.setHeadPose(new EulerAngle(to.getX(), to.getY(), to.getZ()));
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
}