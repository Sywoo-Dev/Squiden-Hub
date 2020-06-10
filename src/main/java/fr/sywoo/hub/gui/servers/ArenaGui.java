package fr.sywoo.hub.gui.servers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.service.ServiceInfoSnapshot;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.enums.Games;
import fr.sywoo.hub.utils.PlayerUtils;

public class ArenaGui extends IQuickInventory {

	public ArenaGui() {
		super("§aChoisis un serveur Arena", 9);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contents(QuickInventory inv) {
		List<String> lores = new ArrayList<String>(Arrays.asList(Games.ARENA.getDescription()));
		lores.add("§2");
		lores.add("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer("Arena"));
		lores.add("§6Développeur : §e" + Games.ARENA.getDevelopper());
		lores.add("§3");
		inv.setItem(new QuickItem(Material.IRON_SWORD).setName("§a§lArena Classique").setLore(lores).toItemStack(), onClick -> {
			onClick.getPlayer().sendMessage("§a§lRecherche d'un serveur.");
			for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
				if(snapshot.getServiceId().getName().startsWith("Arena-")){
					if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
					if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
						onClick.getPlayer().sendMessage("§a§lTéléporation sur " + snapshot.getServiceId().getName());
						LionSpigot.get().getPlayerServerManager().sendPlayerToServer(onClick.getPlayer(), snapshot);
						break;
					}
				}
			}
		}, 2);

		List<String> lores2 = new ArrayList<String>(Arrays.asList(Games.ARENAKIT.getDescription()));
		lores2.add("§2");
		lores2.add("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer("ArenaKit"));
		lores2.add("§6Développeur : §e" + Games.ARENAKIT.getDevelopper());
		lores2.add("§3");
		inv.setItem(new QuickItem(Material.GOLD_SWORD).setName("§a§lArena Kit").setLore(lores2).toItemStack(), onClick -> {
			onClick.getPlayer().sendMessage("§a§lRecherche d'un serveur.");
			for(ServiceInfoSnapshot snapshot : CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices()){
				if(snapshot.getServiceId().getName().startsWith("ArenaKit-")){
					if(ServiceInfoSnapshotUtil.getPlayers(snapshot) == null) continue;
					if(ServiceInfoSnapshotUtil.getPlayers(snapshot).size() < 25){
						onClick.getPlayer().sendMessage("§a§lTéléporation sur " + snapshot.getServiceId().getName());
						LionSpigot.get().getPlayerServerManager().sendPlayerToServer(onClick.getPlayer(), snapshot);
						break;
					}
				}
			}
		}, 6);
	}
}
