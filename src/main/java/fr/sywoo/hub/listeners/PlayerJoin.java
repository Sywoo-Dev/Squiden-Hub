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
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
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
        new TabManager().reloadTab();
        AccountData accountData = LionSpigot.get().getAccountManager().get(player.getUniqueId());
        hub.getClassement().getHolograms().display(player);
        
        if(accountData == null){
            LionSpigot.get().getAccountManager().create(new AccountData(
                    player.getUniqueId(),
                    player.getName(),
                    new Rank(RankEnum.JOUEUR)));
        }
        new BukkitRunnable() {

            @Override
            public void run() {
                hub.getHologramsList().getHolograms().stream().forEach(holograms -> holograms.display(player));
                player.getInventory().clear();
                player.updateInventory();
                player.teleport(new Location(player.getWorld(), 0.5, 15, 0.5));
                player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
                player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
                player.getInventory().setItem(0, new ShopItem().toItemStack());
                player.getInventory().setItem(7, new GuildItem().toItemStack());
                player.getActivePotionEffects().clear();
                player.setWalkSpeed(0.3F);
                player.setGameMode(GameMode.ADVENTURE);
                hub.getScoreboardManager().onLogin(player);
                //new KapturGame(player);
                //new SkywarsGame(player);
                                
                if (accountData.getRank().hasPermission("lionuhc.lobby.fly")) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
                if(accountData.getLastServer() != null) {
                	String server = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getLastServer();
                	if(LionSpigot.get().getServerManager().isExist(server)) {
                		ServersData data = LionSpigot.get().getServerDataManager().get(server);
                		if(data.getType() == GameType.UHC) {
                			UHCData uhc_data = data.getUhcData();
                			if(uhc_data.getPvp() > 0) {
                				player.sendMessage("§e==============================================================");
                				player.sendMessage("§aVous êtes dans §e" + server + "§a, Vous allez être téléporté !");
                				player.sendMessage("§e==============================================================");
                				new BukkitRunnable() {
									
									@Override
									public void run() {
										LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, server);										
									}
								}.runTaskLater(hub, 60);
                			}
                		}
                		if(data.getType() == GameType.KAPTUR) {
            				player.sendMessage("§e==============================================================");
            				player.sendMessage("§aVous êtes dans §e" + server + "§a, Vous allez être téléporté !");
            				player.sendMessage("§e==============================================================");
            				new BukkitRunnable() {
								
								@Override
								public void run() {
									LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, server);										
								}
							}.runTaskLater(hub, 60);
                		}
                	}else {
                		LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId()).setLastServer(null));
                	}
                	
                }
            }
        }.runTaskLater(hub, 20L);
    }

}
