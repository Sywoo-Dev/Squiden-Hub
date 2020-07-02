package fr.sywoo.hub.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.rank.RankEnum;
import fr.sywoo.api.settings.Settings.SettingsEnum;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.TabManager;
import fr.sywoo.api.utils.Title;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.items.GameSelectorItem;
import fr.sywoo.hub.items.GuildItem;
import fr.sywoo.hub.items.LobbySelectorItem;
import fr.sywoo.hub.items.ShopItem;

public class PlayerJoin implements Listener {

	private Hub hub;

	public PlayerJoin(Hub hub){
		this.hub = hub;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event){
		Player player = event.getPlayer();
		event.setJoinMessage(null);

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


		new TabManager().reloadTab();

		hub.getClassement().getHolograms().display(player);
		hub.getClassement2().getHolograms().display(player);
		if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("hub.doublejump")){
			hub.jumps.put(player.getUniqueId(), 0);
			player.setAllowFlight(true);
		}


		player.teleport(new Location(player.getWorld(), 0.5, 64, 0.5));
		new Title().sendTitle(player, 5, 100, 5, "§3» §b§lSquiden §3«", "§a§lplay.squiden.fr");
		new TabManager().reloadTab();
		hub.getScoreboardManager().onLogin(player);
		player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
		player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
		player.getInventory().setItem(0, new ShopItem().toItemStack());
		player.getInventory().setItem(7, new GuildItem().toItemStack());
		player.getActivePotionEffects().clear();
		player.setWalkSpeed(0.3F);
		player.setGameMode(GameMode.ADVENTURE);
		
		new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				player.sendBlockChange(hub.carpet, Material.CARPET, (byte)0);
				player.sendBlockChange(hub.slab, Material.STEP, (byte)3);
			}
		}.runTaskLater(hub, 20);
		
		if(accountData.getSettings().getSeeChat() == SettingsEnum.DISALLOW) {
			for(Player players : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(players);
			}
		}else {
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(!LionSpigot.get().getAccountManager().get(players.getUniqueId()).isVanished()) {
					player.showPlayer(players);
				}
			}
		}
		
		if (accountData.getRank().hasPermission("lionuhc.lobby.fly")) {
			player.setAllowFlight(true);
			if(player.getAllowFlight()) {
				player.setFlying(true);
			}
		}else {
			player.setFlying(false);
		}
	}
}
