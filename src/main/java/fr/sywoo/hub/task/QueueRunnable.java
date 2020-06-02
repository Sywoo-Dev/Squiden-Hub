package fr.sywoo.hub.task;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.sywoo.api.queue.Queue;
import fr.sywoo.api.serverdata.GameType;
import fr.sywoo.api.serverdata.ServerStatus;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.serverdata.uhc.UHCData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.Title;

public class QueueRunnable implements Runnable {

	@Override
	public void run() {
		for(Queue queues : LionSpigot.get().getQueues().values()){
			if(queues.getPlayers().size() > 0) {

				for(String uuid : queues.getPlayers()){
					Player player = Bukkit.getPlayer(UUID.fromString(uuid.replaceFirst(uuid.charAt(0)+"", "")));
					if(player != null) {
						new Title().sendActionBar(player, "§aFile d'attente : §e" + queues.getPosition(uuid) + "§a/§e" + queues.getPlayers().size());
					}
				}
				
				ServersData serverData = queues.getServersData();				
				String uuid = (String) queues.getPlayers().toArray()[0];
				Player player = Bukkit.getPlayer(UUID.fromString(uuid.replaceFirst(uuid.charAt(0)+"", "")));
				if(player == null){
					queues.getPlayers().remove(null);
					queues.getPlayers().remove(uuid);
					continue;
				}

				if (queues.getType() != null) {
					int free = 0;
					for (String str : LionSpigot.get().getServerManager().getServerGroup(queues.getGroup())) {
						ServersData data = LionSpigot.get().getServerDataManager().get(str);
						if(data == null) {
							data = new ServersData(LionSpigot.get().getProjectName(), str, ServerStatus.WAITING, queues.getGroup());
							LionSpigot.get().getServerDataManager().create(data);
						}
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
						String name = LionSpigot.get().getServerManager().createAndGetServerName(queues.getGroup());
						LionSpigot.get().getServerDataManager().create(new ServersData(LionSpigot.get().getProjectName(), name, ServerStatus.WAITING, queues.getGroup()));
					}
				}
				if(queues.getServersData() == null) continue;
				if(queues.getServersData().getType() == null) continue;
				if(queues.getServersData().getType() == GameType.UHCHOST){
					UHCData data = serverData.getUhcData();
					System.out.println("Queue: " + queues.getGame()  + " " + data.isWhitelist());
					if(data.isWhitelist()) {
						if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("moderation.tool")
								|| LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("queue.priority")
								|| serverData.getOwner().equalsIgnoreCase(player.getName())
								|| data.getWhitelisted().contains(player.getUniqueId())){
							queues.removePlayer(player);
							player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
							player.sendMessage("§7§m-----------------------------------------------------");
							player.sendMessage("§aVotre serveur s'est lancé !");
							player.sendMessage("§bTéléportation...");
							player.sendMessage("§cPassage outre de la file d'attente");
							player.sendMessage("§7§m-----------------------------------------------------");
							LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, queues.getServersData().getName());
						}
					} else {
						player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
						player.sendMessage("§7§m-----------------------------------------------------");
						player.sendMessage("§aVotre serveur s'est lancé !");
						player.sendMessage("§bTéléportation...");
						player.sendMessage("§7§m-----------------------------------------------------");
						queues.removePlayer(player);
						LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player, queues.getServersData().getName());
					}
				}
			}
		}
	}
}