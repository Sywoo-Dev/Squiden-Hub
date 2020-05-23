package fr.sywoo.hub.listeners.jump;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.items.GameSelectorItem;
import fr.sywoo.hub.items.GuildItem;
import fr.sywoo.hub.items.LobbySelectorItem;
import fr.sywoo.hub.items.ShopItem;
import fr.sywoo.hub.player.JumpPlayer;

public class JumpInteract implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() == null) return;
        Player player = event.getPlayer();
        if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            switch (event.getItem().getType()){
                case SLIME_BALL:
                    player.teleport(JumpPlayer.getInfos(player).getCheckPoint().getCuboid().getCenter());
                    player.playSound(player.getLocation(), Sound.NOTE_PLING, 10, 10);
                    break;
                case REDSTONE:
                    if(JumpPlayer.getInfos(player) == null) { player.sendMessage("§lJump §l» §cTu n'es pas dans le jump"); return; }
                    player.getInventory().clear();
                    player.updateInventory();
                    player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
                    player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
                    player.getInventory().setItem(7, new GuildItem().toItemStack());
                    player.getInventory().setItem(0, new ShopItem().toItemStack());
                    JumpPlayer.getInfos(player).stop();
                    JumpPlayer.delete(player);
                    player.teleport(new Location(player.getWorld(), 0.5, 16.0, 0.5));
                    player.setWalkSpeed(0.3F);
                    if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.lobby.fly")) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    }
                    player.sendMessage("§lJump §l» §cVous avez quitté le jump.");
                    break;
			default:
				break;
            }
        }
    }

}
