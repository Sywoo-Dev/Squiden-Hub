package fr.sywoo.hub.listeners;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.sywoo.hub.utils.Cuboid;
import fr.sywoo.hub.utils.Location;

public class PlayerMove implements Listener {

    Cuboid spawn = new Cuboid(new Location(-158, 0, -108).getAsLocation(),  new Location(158, 170, 108).getAsLocation());

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if(!spawn.contains(player)){
            player.teleport(new Location(0.5, 64, 0.5).getAsLocation());
            player.sendMessage("§cHop hop ! Le monde est peut être dangereux !");
        }
    }

}
