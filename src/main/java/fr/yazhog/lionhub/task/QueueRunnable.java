package fr.yazhog.lionhub.task;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.yazhog.lionapi.queue.Queue;
import fr.yazhog.lionapi.serverdata.GameType;
import fr.yazhog.lionapi.serverdata.ServerStatus;
import fr.yazhog.lionapi.serverdata.ServersData;
import fr.yazhog.lionapi.serverdata.uhc.UHCData;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionapi.utils.Title;

public class QueueRunnable implements Runnable {

    @Override
    public void run() {
        for(Queue queues : LionSpigot.get().getQueues().values()){
            if(queues.getPlayers().size() > 0) {

                for(UUID uuid : queues.getPlayers()){
                	Player player = Bukkit.getPlayer(uuid);
                	if(player != null) {
                		new Title().sendActionBar(player, "§aFile d'attente : §e" + queues.getPosition(uuid) + "§a/§e" + queues.getPlayers().size());
                	}
                }

                ServersData serverData = LionSpigot.get().getServerDataManager().get(queues.getGame());

                UUID uuid = queues.getPlayers().get(0);
                Player player = Bukkit.getPlayer(uuid);
                if(player == null){
                    queues.getPlayers().remove(null);
                    queues.getPlayers().remove(uuid);
                    continue;
                }
                
                if (queues.getType() != null) {
					if (queues.getType() == GameType.KAPTUR) {
						int free = 0;
						for (String str : LionSpigot.get().getServerManager().getKaptur()) {
							ServersData data = LionSpigot.get().getServerDataManager().get(str);
							if (data.getServerStatus() == ServerStatus.WAITING) {
								free++;
								queues.setServersData(data);
								if(data.canJoin()) {
									player.sendMessage("§aVous aller rejoindre §e" + str);
									LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, data.getName());
								}
								break;
							}
						}
						if(free == 0) {
		            		String name = LionSpigot.get().getServerManager().createAndGetServerName("Kaptur");
		            		LionSpigot.get().getServerDataManager().create(new ServersData("LionUhc", name, ServerStatus.WAITING, GameType.KAPTUR));
						}
					}
					if (queues.getType() == GameType.SKYWARS) {
						int free = 0;
						for (String str : LionSpigot.get().getServerManager().getSkywarsServers()) {
							ServersData data = LionSpigot.get().getServerDataManager().get(str);
							if (data.getServerStatus() == ServerStatus.WAITING) {
								free++;
								queues.setServersData(data);
								if(data.canJoin()) {
									player.sendMessage("§aVous aller rejoindre §e" + str);
									LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, data.getName());
								}
								break;
							}
						}
						if(free == 0) {
		            		String name = LionSpigot.get().getServerManager().createAndGetServerName("Skywars");
		            		LionSpigot.get().getServerDataManager().create(new ServersData("LionUhc", name, ServerStatus.WAITING, GameType.SKYWARS));
						}
					} 
				}
                if(queues.getServersData() == null) continue;
                if(queues.getServersData().getType() == null) continue;
				if(queues.getServersData().getType() == GameType.UHC){
                	UHCData data = serverData.getUhcData();
                	System.out.println("Queue: " + queues.getGame()  + " " + data.isWhitelist());
                	if(data.isWhitelist()) {
                    if(LionSpigot.get().getAccountManager().get(uuid).getRank().hasPermission("moderation.tool")
                    		|| LionSpigot.get().getAccountManager().get(uuid).getRank().hasPermission("queue.priority")
                            || serverData.getOwner().equalsIgnoreCase(player.getName())
                            || data.getWhitelisted().contains(uuid)){
                        queues.removePlayer(player.getUniqueId());
                        player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
                        player.sendMessage("§7§m-----------------------------------------------------");
                        player.sendMessage("§aVotre serveur s'est lancé !");
                        player.sendMessage("§bTéléportation...");
                        player.sendMessage("§cPassage outre de la file d'attente");
                        player.sendMessage("§7§m-----------------------------------------------------");
                        LionSpigot.get().getPlayerServerManager().sendPlayerToServer(uuid, queues.getServersData().getName());
                    }
                } else {
                    player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
                    player.sendMessage("§7§m-----------------------------------------------------");
                    player.sendMessage("§aVotre serveur s'est lancé !");
                    player.sendMessage("§bTéléportation...");
                    player.sendMessage("§7§m-----------------------------------------------------");
                    queues.removePlayer(player.getUniqueId());
                    LionSpigot.get().getPlayerServerManager().sendPlayerToServer(uuid, queues.getServersData().getName());
                }
            }
            }
        }
    }
}