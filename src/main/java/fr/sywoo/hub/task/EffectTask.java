package fr.sywoo.hub.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.hub.Hub;
import fr.sywoo.hub.effects.Effect;
import fr.sywoo.hub.utils.Cuboid;
import net.minecraft.server.v1_8_R3.EnumParticle;

public class EffectTask extends BukkitRunnable{
	
	public Cuboid danceFloor = new Cuboid(new Location(Bukkit.getWorld("world"), 26, 32, 34), new Location(Bukkit.getWorld("world"), 38, 34, 46));
	public Cuboid nightVision = new Cuboid(new Location(Bukkit.getWorld("world"), 50, 46, 19), new Location(Bukkit.getWorld("world"), 19, 33, 57));
	public Cuboid toilettes = new Cuboid(new Location(Bukkit.getWorld("world"), 24, 43, 21), new Location(Bukkit.getWorld("world"), 15, 39, 31));
	public Cuboid piste = new Cuboid(new Location(Bukkit.getWorld("world"), 38, 38, 46), new Location(Bukkit.getWorld("world"), 26, 34, 34));
	public List<Location> pillars = new ArrayList<>();
	public List<Location> lasers = new ArrayList<>();
	public List<Location> lasers2 = new ArrayList<>();
	public List<Block> lamps = new ArrayList<>();
	public boolean nightVisionEffect = false;
	public int actualLamp = 0;
	public Effect effect;
	
	public EffectTask() {
		effect = new Effect(this);
		
		pillars.add(new Location(Bukkit.getWorld("world"),39.5, 34, 38.5));
		pillars.add(new Location(Bukkit.getWorld("world"),39.5, 34, 42.5));
		pillars.add(new Location(Bukkit.getWorld("world"),39.5, 34, 47.5));
		pillars.add(new Location(Bukkit.getWorld("world"),30.5, 34, 47.5));
		pillars.add(new Location(Bukkit.getWorld("world"),34.5, 34, 47.5));
		pillars.add(new Location(Bukkit.getWorld("world"),25.5, 34, 47.5));
		pillars.add(new Location(Bukkit.getWorld("world"),25.5, 34, 42.5));
		pillars.add(new Location(Bukkit.getWorld("world"),25.5, 34, 38.5));
		
		lasers.add(new Location(Bukkit.getWorld("world"), 42.5, 44, 36.5));
		lasers.add(new Location(Bukkit.getWorld("world"), 22.5, 44, 36.5));
		lasers.add(new Location(Bukkit.getWorld("world"), 36.5, 44, 50.5));
		lasers.add(new Location(Bukkit.getWorld("world"), 28.5, 44, 50.5));
		lasers2.add(new Location(Bukkit.getWorld("world"), 20.5, 44, 45.5));
		lasers2.add(new Location(Bukkit.getWorld("world"), 22.5, 44, 50.5));
		lasers2.add(new Location(Bukkit.getWorld("world"), 26.5, 44, 36.5));
		
		effect.initLasers(lasers);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				Location pillar = pillars.get(new Random().nextInt(pillars.size()));
				effect.createCyl(pillar.clone(), EnumParticle.REDSTONE, 10, 1);
			}
		}.runTaskTimer(Hub.instance, 0, 10);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(new Random().nextBoolean()) {
					for(Player players : Bukkit.getOnlinePlayers()) {
						if(nightVision.contains(players) && !toilettes.contains(players)) {
							if(nightVisionEffect) {
								players.removePotionEffect(PotionEffectType.NIGHT_VISION);
							}else {
								players.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 9999, 0, false, false));
							}
							nightVisionEffect = !nightVisionEffect;
						}
					}
				}
			}
		}.runTaskTimer(Hub.instance, 0, 20);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				effect.changeLasersPositions();
			}
		}.runTaskTimer(Hub.instance, 0, 10);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				new BukkitRunnable() {
					
					@Override
					public void run() {
						effect.changeLasers2Positions();
					}
				}.runTaskTimer(Hub.instance, 0, 10);
			}
		}.runTaskLater(Hub.instance, 6);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if(lamps.isEmpty()) {
			BlockFace[] faces = {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
			Block b = Bukkit.getWorld("world").getBlockAt(new Location(Bukkit.getWorld("world"), 25, 33, 34));
			lamps.add(b);
			for(int i = 0; i < 60; i++) {
				for(BlockFace f : faces) {
					if(b.getRelative(f).getType() == Material.REDSTONE_LAMP_OFF) {
						lamps.add(b.getRelative(f));
						b = b.getRelative(f);
					}
				}
			}
			
			System.out.println("NightBox Init");
			System.out.println("Lamps size " + lamps.size());
		}
		
		lamps.get(actualLamp).setType(Material.REDSTONE_LAMP_ON);
		BlockFace[] faces = {BlockFace.EAST, BlockFace.WEST, BlockFace.NORTH, BlockFace.SOUTH};
		for(BlockFace f : faces) {
			if(lamps.get(actualLamp).getRelative(f).getType() == Material.REDSTONE_LAMP_ON) {
				BlockState state = lamps.get(actualLamp).getRelative(f).getState();
				state.setType(Material.REDSTONE_LAMP_OFF);
				state.update(true, false);
			}
		}
		actualLamp++;
		if(actualLamp >= lamps.size()-1) {
			actualLamp = 0;
		}
		
		for(Block blocks : danceFloor.getBlocks()) {
			byte data = (byte) new Random().nextInt(15);
			if(blocks.getType() == Material.STAINED_GLASS) {
				blocks.setData(data);
			}
		}
		
	}

}
