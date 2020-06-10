package fr.sywoo.hub.gui.servers.uhcs;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.serverdata.uhc.UHCData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.uhcconfig.UhcType;
import fr.sywoo.hub.utils.FormatTime;
import fr.sywoo.hub.utils.PlayerUtils;

public class LgGUI extends IQuickInventory {

	public LgGUI() {
		super("§aLoup-Garou UHC", 9*5);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contents(QuickInventory quickInventory) {
		quickInventory.updateItem("servers", taskUpdate -> {
			for(int i = 0; i < ((9*5) -1); i++){
				quickInventory.setItem(new ItemStack(Material.AIR), i);
			}
			quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
			quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 35, 44);
			quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 35);
			quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 44);
			CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
			.filter(serviceInfoSnapshot -> serviceInfoSnapshot.getServiceId().getName().startsWith("UHC-"))
			.forEach(serviceInfoSnapshot -> {
				ServersData serverData = LionSpigot.get().getServerDataManager().get(serviceInfoSnapshot.getServiceId().getName());
				UHCData data = serverData.getUhcData();
				if(data.getUhcType() == UhcType.LG) {
					List<String> lore = new ArrayList<>();
					lore.add("§8§m---------------");
					lore.add("§7§lHost §8» §b" + serverData.getOwner());
					lore.add("§7§lPartie §8» §e" + data.getType());
					lore.add("§7§lStatus §8» §6" + serverData.getServerStatus().getName());
					lore.add("§8§m---------------");
					lore.add("§7§lJoueurs §8» §a" + PlayerUtils.getPlayers(serviceInfoSnapshot) + "§e/§a" + ServiceInfoSnapshotUtil.getMaxPlayers(serviceInfoSnapshot));
					lore.add("§7§lPvP §8§8» §c" + new FormatTime(data.getPvp()).toString());
					lore.add("§7§lBorder §8§8» §c" + new FormatTime(data.getBorder()).toString());
					lore.add("§8§m---------------");
					for(String str : data.getScenarios()){
						lore.add(" §8• §c" + str);
					}
					String customName = data.getCustomName();
					if(customName == null){
						customName = "§6§lUHCHost";
					}

					if(data.getWhitelisted().contains(quickInventory.getOwner().getUniqueId())) { lore.add("§aVous êtes whitelist !"); }
					quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 14)
							.setName(customName)
							.setLore(lore)
							.toItemStack(), quickEvent -> {
								LionSpigot.get().addPlayerInWaitingQueue(quickEvent.getPlayer(), serviceInfoSnapshot.getServiceId().getName());
							});	
				}
			});
		}, 20);
		quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 10, 28);
		quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 16, 34);
	}

}