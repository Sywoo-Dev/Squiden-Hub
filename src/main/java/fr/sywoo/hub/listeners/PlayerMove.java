package fr.sywoo.hub.listeners;

import fr.sywoo.hub.utils.Cuboid;
import fr.sywoo.hub.utils.Location;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

    Cuboid spawn = new Cuboid(new Location(100, 0, 101).getAsLocation(),
            new Location(-163, 170, -97).getAsLocation());

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(player.getGameMode() == GameMode.CREATIVE) return;
        if(!spawn.contains(player)){
            player.teleport(new Location(0, 16, 0).getAsLocation());
            player.sendMessage("§cHop hop ! Ne t'éloigne pas trop Cow Boy, on ne sait jamais ce qui peut arriver ! \\o/");
        }
    }

}
