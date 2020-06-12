package fr.sywoo.hub.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.hub.gui.servers.ArenaGui;
import fr.sywoo.hub.gui.servers.HikabrainGUI;
import fr.sywoo.hub.gui.servers.UHCGui;

public enum Games {

	ARENA(12, new ItemStack(Material.IRON_SWORD), new ArenaGui(), "Arena","Sywoo", "Arena", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	ARENAKIT(0, new ItemStack(Material.GOLD_SWORD), new ArenaGui(), "Arena Kit","Sywoo", "Arena", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	HIKABRAIN(14, new ItemStack(Material.SANDSTONE), new HikabrainGUI(), "Hikabrain","Maygo", "Hikabrain", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	KAPTUR(22, new ItemStack(Material.BEACON), null, "Kaptur", "Sywoo","Kaptur", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	UHCRUN(20, new ItemStack(Material.GOLDEN_CARROT), null, "UhcRun","Maygo", "UhcRun", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	SKYWARS(24, new ItemStack(Material.FEATHER), null, "Skywars","Sywoo", "Skywars", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	SLASHER(32, new ItemStack(Material.IRON_AXE), null, "Slasher","Maygo", "Slasher", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	GOLEMRUSH(30, new ItemStack(Material.IRON_BLOCK), null, "GolemRush","Maygo", "GolemRush", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	AGEOFEMPIRE(42, new ItemStack(Material.GOLD_PICKAXE), null, "AgeOfEmpire","Sywoo", "AOE", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	UHCHOST(38, new ItemStack(Material.GOLDEN_APPLE), new UHCGui(), "UHC Host","Sywoo & Maygo", "UHC", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE");

	
	private String name, dev, group, annotation;
	private String[] description;
	private ItemStack icon;
	private IQuickInventory inventory;
	private int slot;
	
	private Games(int slot, ItemStack icon, IQuickInventory inventory, String name,String developper, String group, String annotation, String... description) {
		this.name = name;
		this.dev = developper;
		this.group = group;
		this.annotation = annotation;
		this.description = description;
		this.icon = icon;
		this.inventory = inventory;
		this.slot = slot;
	}
	
	public int getSlot() {
		return slot;
	}
	
	public String getName() {
		return name;
	}
	public String getDevelopper() {
		return dev;
	}
	public String getGroup() {
		return group;
	}
	public String getAnnotation() {
		return annotation;
	}
	public String[] getDescription() {
		return description;
	}
	public ItemStack getIcon() {
		return icon;
	}
	
	public IQuickInventory getInventory() {
		return inventory;
	}
}
