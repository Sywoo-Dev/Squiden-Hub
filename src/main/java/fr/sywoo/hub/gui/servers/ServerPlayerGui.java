package fr.sywoo.hub.gui.servers;

import java.util.HashMap;
import java.util.Map;
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
import fr.sywoo.api.queue.Queue;
import fr.sywoo.api.serverdata.GameType;
import fr.sywoo.api.serverdata.ServerStatus;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.Title;
import fr.sywoo.hub.Hub;

public class ServerPlayerGui extends IQuickInventory {

	private Hub hub;
	private BukkitTask uhc;
	private static Map<UUID, String> playerStartedServer = new HashMap<>();

	public ServerPlayerGui(Hub hub) {
		super("§6Crée ton serveur", 9*4);
		this.hub = hub;
	}

	@Override
	public void contents(QuickInventory quickInventory) {
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 26, 35);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 26);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 35);

		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§6UHC")
				.setLore("", "§bLancez votre propre host UHC afin de profiter un", ChatColor.AQUA + "maximum des customisations de §6Lion§cUHC !", "").toItemStack(), quickEvent -> {
					quickEvent.getPlayer().closeInventory();
					if(playerStartedServer.containsKey(quickEvent.getPlayer().getUniqueId())){
						quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
						quickEvent.getPlayer().sendMessage("§cTu as déjà un serveur en cours de lancement, tu ne peux pas en lancer un autre");
						return;
					}
					if(LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).isHost()){
						String name = LionSpigot.get().getServerManager().createAndGetServerName("UHC");
						playerStartedServer.put(quickEvent.getPlayer().getUniqueId(), name);
						LionSpigot.get().getServerDataManager().create(new ServersData(quickEvent.getPlayer().getName(), name, ServerStatus.WAITING, GameType.UHCHOST));
						uhc = Bukkit.getScheduler().runTaskTimer(hub, new Runnable() {
							int i = 0;
							@Override
							public void run() {
								i++;
								new Title().sendActionBar(quickEvent.getPlayer(), "§cChargement du serveur : §3" + getProgressBar(i, 20, 40, '|', ChatColor.GREEN, ChatColor.GRAY));
								if(i == 20){
									playerStartedServer.remove(quickEvent.getPlayer().getUniqueId());
									LionSpigot.get().getPlayerServerManager().sendPlayerToServer(quickEvent.getPlayer(), name);
									LionSpigot.get().getQueues().put(name, new Queue(name));
									uhc.cancel();
								}
							}
						}, 20, 20);
					} else {
						quickEvent.getPlayer().sendMessage(ChatColor.RED + "Il faut être Host pour pouvoir lancer un serveur à la demande");
						quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
					}
				} ,11);
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
