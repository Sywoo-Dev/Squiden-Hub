package fr.yazhog.lionhub.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.yazhog.lionapi.queue.Queue;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.gui.servers.ServerPlayerGui;

public class PlayerQuit implements Listener {

    private Hub hub;

    public PlayerQuit(Hub hub){
        this.hub = hub;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(Queue.playerInQueue(player)){
            if(Queue.getPlayerQueue(player).getPlayers() == null) return;
            Queue.getPlayerQueue(player).getPlayers().remove(player.getUniqueId());
        }
                
        event.setQuitMessage("§7[§c-§7] " + player.getName());
        hub.getScoreboardManager().onLogout(player);
        hub.getHologramsList().getHolograms().forEach(holograms -> holograms.destroy(player));
        if(ServerPlayerGui.getPlayerStartedServer().containsKey(player.getUniqueId())){
            LionSpigot.get().getServerManager().deleteServer(ServerPlayerGui.getPlayerStartedServer().get(player.getUniqueId()));
            ServerPlayerGui.getPlayerStartedServer().remove(player.getUniqueId());
        }
    }

}
