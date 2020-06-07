package fr.sywoo.hub.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.servers.ServerPlayerGui;

public class PlayerQuit implements Listener {

    private Hub hub;

    public PlayerQuit(Hub hub){
        this.hub = hub;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(null);
                
        hub.getScoreboardManager().onLogout(player);
        if(ServerPlayerGui.getPlayerStartedServer().containsKey(player.getUniqueId())){
            LionSpigot.get().getServerManager().deleteServer(ServerPlayerGui.getPlayerStartedServer().get(player.getUniqueId()));
            ServerPlayerGui.getPlayerStartedServer().remove(player.getUniqueId());
        }
    }

}
