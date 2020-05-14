package fr.yazhog.lionhub.listeners;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.yazhog.lionapi.account.AccountData;
import fr.yazhog.lionapi.rank.Rank;
import fr.yazhog.lionapi.rank.RankEnum;
import fr.yazhog.lionapi.serverdata.GameType;
import fr.yazhog.lionapi.serverdata.ServersData;
import fr.yazhog.lionapi.serverdata.uhc.UHCData;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.items.GameSelectorItem;
import fr.yazhog.lionhub.items.GuildItem;
import fr.yazhog.lionhub.items.LobbySelectorItem;
import fr.yazhog.lionhub.items.ShopItem;

public class PlayerJoin implements Listener {

    private Hub hub;

    public PlayerJoin(Hub hub){
        this.hub = hub;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        hub.getClassement().getHolograms().display(player);
        if(LionSpigot.get().getAccountManager().get(player.getUniqueId()) == null){
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
                                
                if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.lobby.fly")) {
                    player.setAllowFlight(true);
                    player.setFlying(true);
                }
                if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getLastServer() != null) {
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
