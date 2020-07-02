package fr.sywoo.hub.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.servers.LaunchServerGUI;

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
        if(LaunchServerGUI.getPlayerStartedServer().containsKey(player.getUniqueId())){
            LionSpigot.get().getServerManager().deleteServer(LaunchServerGUI.getPlayerStartedServer().get(player.getUniqueId()));
            LaunchServerGUI.getPlayerStartedServer().remove(player.getUniqueId());
        }
    }

}
