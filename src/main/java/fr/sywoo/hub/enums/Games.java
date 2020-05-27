package fr.sywoo.hub.enums;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.hub.gui.servers.ArenaGui;
import fr.sywoo.hub.gui.servers.UHCGui;

public enum Games {

	ARENA(new ItemStack(Material.IRON_SWORD), new ArenaGui(), "Arena", "Arena", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	HIKABRAIN(new ItemStack(Material.SANDSTONE), null, "Hikabrain", "Hikabrain", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	KAPTUR(new ItemStack(Material.BEACON), null, "Kaptur", "Kaptur", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	UHCRUN(new ItemStack(Material.GOLDEN_CARROT), null, "UHCRun", "UHCRun", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	SKYWARS(new ItemStack(Material.FEATHER), null, "Skywars", "Skywars", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	SLASHER(new ItemStack(Material.IRON_AXE), null, "Slasher", "Slasher", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	GOLEMRUSH(new ItemStack(Material.IRON_BLOCK), null, "GolemRush", "GolemRush", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	AGEOFEMPIRE(new ItemStack(Material.GOLD_PICKAXE), null, "AgeOfEmpire", "AOE", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE"),
	UHCHOST(new ItemStack(Material.GOLDEN_APPLE), new UHCGui(), "UHC Host", "UHC", "§d§k|§r §e§lBêta §d§k|", "Insert", "Description", "HERE");

	
	private String name, group, annotation;
	private String[] description;
	private ItemStack icon;
	private IQuickInventory inventory;
	
	private Games(ItemStack icon, IQuickInventory inventory, String name, String group, String annotation, String... description) {
		this.name = name;
		this.group = group;
		this.annotation = annotation;
		this.description = description;
		this.icon = icon;
		this.inventory = inventory;
	}
	
	public String getName() {
		return name;
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
