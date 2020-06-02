package fr.sywoo.hub.animas;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import fr.sywoo.hub.Hub;

public abstract class Animatronic implements Listener{
	
	private boolean blinking, walking, salute, action;
	private ChatColor first, second;
	private int blinkTime, actionTime, blinkStat;
	private String name;
	private Location loc;
	private ArmorStand stand;
	private ItemStack[] items;
		
	public Animatronic(Location loc, String name, ChatColor first, ChatColor second, int blinkTime) {
		this.name = name;
		this.first = first;
		this.second = second;
		this.blinkTime = blinkTime;
		this.blinking = true;
		this.loc = loc;
		this.blinkStat = 0;
	}
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		if(event.getRightClicked() == null) return;
		if(event.getRightClicked().getCustomName() == null) return;
		if(!(event.getRightClicked().getEntityId() == stand.getEntityId())) return;
		onClick(event);
	}
	
	public Animatronic(Location loc, String name) {
		this.blinking = false;
		this.name = name;
		this.loc = loc;
	}
	
	public Animatronic setWalking(boolean walking) {
		this.walking = walking;
		return this;
	}
	
	public Animatronic setSalute(boolean salute) {
		this.salute = salute;
		return this;
	}
	
	public Animatronic setAction(int actionSpeed) {
		this.action = true;
		this.actionTime = actionSpeed;
		return this;
	}
	
	public Animatronic setStuff(ItemStack... items) {
		this.items = items;
		return this;
	}
	
	public Animatronic setHead(String hash) {
		items[0] = applyTextures(hash);
		return this;
	}

	private ItemStack applyTextures(String hash) {
		
		GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
		PropertyMap propertyMap = gameProfile.getProperties();
		propertyMap.put("textures", new Property("textures", hash));
		
		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (byte) SkullType.PLAYER.ordinal());
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		Class<?> c_skullMeta = skullMeta.getClass();
		try {
			Field f_profile = c_skullMeta.getDeclaredField("profile");
			f_profile.setAccessible(true);
			f_profile.set(skullMeta, gameProfile);
			f_profile.setAccessible(false);
			itemStack.setItemMeta(skullMeta);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		
		return itemStack;
	}
	
	public void spawnAndPlay() {
		this.stand = (ArmorStand) this.loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
		stand.getLocation().setYaw(loc.getYaw());
		stand.setCustomName(name);
		stand.setCustomNameVisible(true);
		stand.setBasePlate(false);
		stand.setArms(true);
		stand.setHelmet(items[0]);
		stand.setChestplate(items[1]);
		stand.setLeggings(items[2]);
		stand.setBoots(items[3]);
		stand.setItemInHand(items[4]);
		stand.setGravity(false);
		stand.setVisible(true);
		Bukkit.getPluginManager().registerEvents(this, Hub.instance);
		if(blinking) {
			blink();
		}
		if(walking) {
			walk();
		}
		if(salute) {
			salute();
		}
		if(action) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					executeAction();
				}
			}.runTaskTimer(Hub.instance, actionTime, actionTime);
		}
	}
	
	private void walk() {
		new BukkitRunnable() {
			int phase = 0;
			@Override
			public void run() {
				double min = 1.11;
				double max = -1.11;
				EulerAngle rightLeg = stand.getRightLegPose();
				
				if(rightLeg.getX() < min && phase == 0) {
					rightLeg = rightLeg.add(0.1, 0, 0);
				}
				
				if(rightLeg.getX() > max && phase == 1) {
					rightLeg = rightLeg.add(-0.1, 0, 0);
				}
				
				
				if(rightLeg.getX() <= max && phase == 1) {
					phase = 0;
				}
				
				if(rightLeg.getX() >= min && phase == 0) {
					phase = 1;
				}
				
				stand.setRightLegPose(rightLeg);
				stand.setLeftLegPose(rightLeg.setX(-rightLeg.getX()));
				
				stand.setLeftArmPose(rightLeg);
				stand.setRightArmPose(rightLeg.setX(-rightLeg.getX()));
			}
		}.runTaskTimer(Hub.instance, 0, 1);
	}
	
	private void salute() {
		new BukkitRunnable() {
			int phase = 0;
			@Override
			public void run() {
				double min = 0.2D;
				double max = 1D;
				EulerAngle angle = stand.getLeftArmPose();
				angle = angle.setX(3D);
				
				if(phase == 0 && angle.getZ() < max) {
					angle = angle.add(0, 0, 0.1);
				}
				
				if(phase == 1 && angle.getZ() > min) {
					angle = angle.add(0, 0, -0.1);
				}
				
				if(phase == 0 && angle.getZ() >= max) {
					phase = 1;
				}
				
				if(phase == 1 && angle.getZ() <= min) {
					phase = 0;
				}
				
				stand.setLeftArmPose(angle);
				
			}
		}.runTaskTimer(Hub.instance, 0, 1);
	}
	
	private void blink() {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(blinkStat == 0) {
					blinkStat++;
					stand.setCustomName(first + name);
				}else {
					blinkStat = 0;
					stand.setCustomName(second + name);
				}
			}
		}.runTaskTimer(Hub.instance, blinkTime, blinkTime);
	}

	public abstract void executeAction();
	public abstract void onClick(PlayerInteractAtEntityEvent event);

	public boolean isBlinking() {
		return blinking;
	}

	public void setBlinking(boolean blinking) {
		this.blinking = blinking;
	}

	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public ChatColor getFirst() {
		return first;
	}

	public void setFirst(ChatColor first) {
		this.first = first;
	}

	public ChatColor getSecond() {
		return second;
	}

	public void setSecond(ChatColor second) {
		this.second = second;
	}

	public int getBlinkTime() {
		return blinkTime;
	}

	public void setBlinkTime(int blinkTime) {
		this.blinkTime = blinkTime;
	}

	public int getActionTime() {
		return actionTime;
	}

	public void setActionTime(int actionTime) {
		this.actionTime = actionTime;
	}

	public int getBlinkStat() {
		return blinkStat;
	}

	public void setBlinkStat(int blinkStat) {
		this.blinkStat = blinkStat;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public ArmorStand getStand() {
		return stand;
	}

	public void setStand(ArmorStand stand) {
		this.stand = stand;
	}

	public ItemStack[] getItems() {
		return items;
	}

	public void setItems(ItemStack[] items) {
		this.items = items;
	}

	public boolean isWalking() {
		return walking;
	}

	public boolean isSalute() {
		return salute;
	}
	
}
