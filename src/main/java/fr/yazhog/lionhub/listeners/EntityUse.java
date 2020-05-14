package fr.yazhog.lionhub.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

import fr.yazhog.lionapi.queue.Queue;
import fr.yazhog.lionapi.serverdata.GameType;
import fr.yazhog.lionapi.serverdata.ServerStatus;
import fr.yazhog.lionapi.serverdata.ServersData;
import fr.yazhog.lionapi.spigot.LionSpigot;

public class EntityUse implements Listener {
	
	@EventHandler
	public void onInteract(PlayerInteractAtEntityEvent event) {
		System.out.println(event.getRightClicked().getName());
		if(event.getRightClicked() instanceof Player) {
			if(event.getRightClicked().getName().contains("Kaptur")) {
				if(LionSpigot.get().getServerManager().getKaptur().size() == 0) {
					String name = LionSpigot.get().getServerManager().createAndGetServerName("Kaptur");
					LionSpigot.get().getServerDataManager().create(new ServersData("LionUhc", name, ServerStatus.WAITING, GameType.KAPTUR));
				}
				if(!Queue.existFor("Kaptur")) {
					new Queue("Kaptur", GameType.KAPTUR);
				}
				Queue.getByName("Kaptur").addPlayer(event.getPlayer().getUniqueId());
			}
		}
	}

}
