package fr.sywoo.hub.enums;

import org.bukkit.Material;

public enum UhcBiome {

	OCEAN(0, "Ocean", Material.WATER_BUCKET),
	PLAIN(1, "Plains", Material.GRASS),
	DESERT(2, "Desert", Material.SAND),
	EXTREMHILLS(3, "Extrem Hills", Material.STONE),
	FOREST(4, "Forest", Material.LEAVES),
	TAIGA(5, "Savane", Material.LEAVES_2),
	SWAMP(6, "Marrais", Material.WATER_LILY),
	RIVER(7, "Rivière", Material.WATER_BUCKET),
	FROZEOCEAN(10, "Ocean Gelé", Material.ICE),
	FROZENRIVER(11, "Rivière gelé", Material.ICE),
	ICEPLAIN(12, "Plaine Gelé", Material.SNOW_BLOCK),
	ICEMONTAIN(13, "Montagne gelé", Material.SNOW),
	MUSHROOMISLAND(14, "Ile aux Champignons", Material.RED_MUSHROOM),
	MUSHROOMISLAND2(15, "Ile aux champignon 2", Material.RED_MUSHROOM),
	BEACH(16, "Plage", Material.SAND),
	DESERTHILL(17, "Desert extrème", Material.SAND),
	FORESTHILL(18, "Forêt dense", Material.LEAVES),
	TAIGAHILL(19, "Savane extrême", Material.LONG_GRASS),
	EXTREMEDGE(20, "Montagnes", Material.STONE),
	JUNGLE(21, "Jungle", Material.VINE),
	JUNGLEHILLS(22, "Jungle Extrême", Material.VINE),
	JUNGLEEDGE(23, "Jungle plateau", Material.VINE),
	DEEPOCEAN(24, "Ocean Profond", Material.WATER_BUCKET),
	STONEBEACH(25, "Plage de cailloux", Material.COBBLESTONE),
	COLDBEACH(26, "Plage glacé", Material.ICE),
	BIRCHFOREST(27, "Foret blanche", Material.LOG),
	BIRCHFORESTHILLS(28, "Foret blanche dense", Material.LOG),
	ROOFEDFOREST(29, "Foret aux champigons §e(Idéale LG)", Material.BROWN_MUSHROOM),
	COLDTAIGA(30, "Savane froide", Material.SNOW),
	COLDTAIGAHILLS(31, "Savane froide extrême", Material.WATER_BUCKET),
	MEGATAIGA(32, "Savane géante", Material.RED_SANDSTONE),
	MEGATAIGAHILLS(33, "Savane géante extrême", Material.RED_SANDSTONE),
	EXTREMHILLS2(34, "Extrem Hills 2", Material.STONE),
	SAVANA(35, "Savane", Material.SAND),
	SAVANAPLATEAU(36, "Plateau de Savane", Material.SAND),
	MESA(37, "Canyon", Material.STAINED_CLAY),
	MESAPLATEAU(38, "Canyon Plat", Material.STAINED_CLAY),
	MESAPLATEAU2(39, "Canyon Plat 2", Material.STAINED_CLAY);
	
	private int id;
	private String name;
	private Material icon;

	private UhcBiome(int id, String name, Material icon) {
		this.id = id;
		this.name = name;
		this.icon = icon;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Material getIcon() {
		return icon;
	}

}
