package fr.sywoo.hub.task;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.hub.utils.Cuboid;

public class EffectTask extends BukkitRunnable{

	private Cuboid danceFloor = new Cuboid(new Location(Bukkit.getWorld("world"), 26, 32, 34), new Location(Bukkit.getWorld("world"), 38, 34, 46));
	private ArrayList<Block> lamps = new ArrayList<Block>();
	private int actualLamp = 0;
	
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
				lamps.get(actualLamp).getRelative(f).setType(Material.REDSTONE_LAMP_OFF);
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
