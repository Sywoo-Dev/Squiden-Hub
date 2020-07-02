package fr.sywoo.hub.gui.servers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.base.Strings;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.serverdata.GameType;
import fr.sywoo.api.serverdata.ServerStatus;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.serverdata.uhc.UHCData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.Title;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.enums.UhcBiome;

public class LaunchServerGUI extends IQuickInventory{

	private Hub hub;
	private BukkitTask uhc;
	private static Map<UUID, String> playerStartedServer = new HashMap<>();

	public LaunchServerGUI(Hub hub) {
		super("§aChoix des biomes", 54);
		this.hub = hub;
	}
	
	@Override
	public void contents(QuickInventory inv) {

		Set<Integer> selectedBiomes = new HashSet<Integer>();
		
		inv.updateItem("update", onUpdate -> {
			for(int i = 0; i < 53; i++) {
				inv.setItem(new QuickItem(Material.AIR).toItemStack(), i);
			}
			for(UhcBiome biomes : UhcBiome.values()) {
				inv.addItem(new QuickItem(biomes.getIcon()).setName(biomes.getName()).setLore(selectedBiomes.contains(biomes.getId()) ? "§aOui" : "§cNon").toItemStack(), onClick -> {
					if(selectedBiomes.contains(biomes.getId())) {
						selectedBiomes.remove(biomes.getId());
					}else {
						selectedBiomes.add(biomes.getId());
					}
				});
			}
		}, 1);
		
		
		inv.setItem(new QuickItem(Material.SLIME_BALL).setName("§a§lLancer le serveur").setLore("§7Choisissez bien vos biomes", "§cAvant de lancer !").toItemStack(), onClick -> {
			if(playerStartedServer.containsKey(onClick.getPlayer().getUniqueId())){
				onClick.getPlayer().playSound(onClick.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
				onClick.getPlayer().sendMessage("§cTu as déjà un serveur en cours de lancement, tu ne peux pas en lancer un autre");
				return;
			}
			String name = LionSpigot.get().getServerManager().createAndGetServerName("UHC");
			LionSpigot.get().getServerDataManager().create(new ServersData(onClick.getPlayer().getName(), name, ServerStatus.WAITING, GameType.UHCHOST));
			UHCData data = LionSpigot.get().getServerDataManager().get(name).getUhcData();
			data.setBiomes(selectedBiomes);
			LionSpigot.get().getServerDataManager().update(LionSpigot.get().getServerDataManager().get(name).setUhcData(data));
			playerStartedServer.put(onClick.getPlayer().getUniqueId(), name);
			onClick.getPlayer().sendMessage("§aVotre Serveur à été paramètré, Lancement en cours !");
			onClick.getPlayer().closeInventory();
			
			uhc = Bukkit.getScheduler().runTaskTimer(hub, new Runnable() {
				int i = 0;
				@Override
				public void run() {
					i++;
					new Title().sendActionBar(onClick.getPlayer(), "§cChargement du serveur : §3" + getProgressBar(i, 20, 40, '|', ChatColor.GREEN, ChatColor.GRAY));
					if(i == 20){
						playerStartedServer.remove(onClick.getPlayer().getUniqueId());
						LionSpigot.get().getPlayerServerManager().sendPlayerToServer(onClick.getPlayer(), name);
						uhc.cancel();
					}
				}
			}, 20, 20);
		}, 53);
	}
	
	
	public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
			ChatColor notCompletedColor) {
		float percent = (float) current / max;
		int progressBars = (int) (totalBars * percent);

		return Strings.repeat("" + completedColor + symbol, progressBars)
				+ Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
	}

	public static Map<UUID, String> getPlayerStartedServer() {
		return playerStartedServer;
	}
}
