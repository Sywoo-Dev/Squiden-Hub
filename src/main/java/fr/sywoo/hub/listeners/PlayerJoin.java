package fr.sywoo.hub.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.rank.RankEnum;
import fr.sywoo.api.serverdata.GameType;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.serverdata.uhc.UHCData;
import fr.sywoo.api.settings.Settings.SettingsEnum;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.Title;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.classements.HikabrainClassements;
import fr.sywoo.hub.items.GameSelectorItem;
import fr.sywoo.hub.items.GuildItem;
import fr.sywoo.hub.items.LobbySelectorItem;
import fr.sywoo.hub.items.ShopItem;
import fr.sywoo.hub.utils.TabManager;

public class PlayerJoin implements Listener {

	private Hub hub;

	public PlayerJoin(Hub hub){
		this.hub = hub;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		event.setJoinMessage(null);

		
		hub.getClassement().getHolograms().display(player);
		hub.jumps.put(player.getUniqueId(), 0);

		if(LionSpigot.get().getAccountManager().get(player.getUniqueId()) == null){
			LionSpigot.get().getAccountManager().create(new AccountData(
					player.getUniqueId(),
					player.getName(),
					new Rank(RankEnum.JOUEUR)));

		}
		AccountData accountData = LionSpigot.get().getAccountManager().get(player.getUniqueId());

		if(accountData.getRank().hasPermission("hub.join")) {
			event.setJoinMessage(accountData.getPrefix() + player.getName() + " §aA rejoint le Hub !");
		}


		player.teleport(new Location(player.getWorld(), 0.5, 64, 0.5));
		new Title().sendTitle(player, 5, 100, 5, "§3» §b§lSquiden §3«", "§a§lplay.squiden.fr");
		new TabManager().reloadTab();
		hub.getScoreboardManager().onLogin(player);

		hub.getHologramsList().getHolograms().stream().forEach(holograms -> holograms.display(player));
		player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
		player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
		player.getInventory().setItem(0, new ShopItem().toItemStack());
		player.getInventory().setItem(7, new GuildItem().toItemStack());
		player.getActivePotionEffects().clear();
		player.setWalkSpeed(0.3F);
		player.setGameMode(GameMode.ADVENTURE);
		player.setAllowFlight(true);

		if (accountData.getRank().hasPermission("lionuhc.lobby.fly")) {
			player.setFlying(true);
		}else {
			player.setFlying(false);
		}
		
		new HikabrainClassements().display(player);
		
		if(accountData.getLastServer() != null) {
			String server = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getLastServer();
			if(LionSpigot.get().getServerManager().isExist(server)) {
				ServersData data = LionSpigot.get().getServerDataManager().get(server);
				if(data.getType() == GameType.UHCHOST) {
					UHCData uhc_data = data.getUhcData();
					if(uhc_data.getPvp() > 0) {
						if (accountData.getSettings().getAutoReconnect() == SettingsEnum.ALLOW) {
							player.sendMessage("§e==============================================================");
							player.sendMessage("§aVous êtes dans §e" + server + "§a, Vous allez être téléporté !");
							player.sendMessage("§e==============================================================");
							new BukkitRunnable() {

								@Override
								public void run() {
									LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, server);
								}
							}.runTaskLater(hub, 60);
						}else {
							player.sendMessage("§e==============================================================");
							player.sendMessage("§aVous êtes dans §e" + server + "§a, faite /rejoin pour revenir en jeu !");
							player.sendMessage("§e==============================================================");
						}
					}
				}
				if(data.getType() == GameType.KAPTUR || data.getType() == GameType.AGEOFEMPIRE || data.getType() == GameType.UHCRUN) {
					if (accountData.getSettings().getAutoReconnect() == SettingsEnum.ALLOW) {
						player.sendMessage("§e==============================================================");
						player.sendMessage("§aVous êtes dans §e" + server + "§a, Vous allez être téléporté !");
						player.sendMessage("§e==============================================================");
						new BukkitRunnable() {

							@Override
							public void run() {
								LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, server);
							}
						}.runTaskLater(hub, 60);
					}else {
						player.sendMessage("§e==============================================================");
						player.sendMessage("§aVous êtes dans §e" + server + "§a, faite /rejoin pour revenir en jeu !");
						player.sendMessage("§e==============================================================");
					}
				}
				
			}else {
				LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId()).setLastServer(null));
			}

		}
	}
}
