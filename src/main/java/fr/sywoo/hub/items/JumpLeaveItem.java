package fr.sywoo.hub.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.player.JumpPlayer;

public class JumpLeaveItem extends QuickItem{

	public JumpLeaveItem() {
		super(Material.REDSTONE);
		setName("§c§lQuitter le Jump").setAction(action -> {
			Player player = action.getPlayer();
            if(JumpPlayer.getInfos(player) == null) { player.sendMessage("§lJump §l» §cTu n'es pas dans le jump"); return; }
            player.getInventory().clear();
            player.updateInventory();
            player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
            player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
            player.getInventory().setItem(7, new GuildItem().toItemStack());
            player.getInventory().setItem(0, new ShopItem().toItemStack());
            JumpPlayer.getInfos(player).stop();
            JumpPlayer.delete(player);
            player.teleport(new Location(player.getWorld(), 0.5, 64, 0.5));
            player.setWalkSpeed(0.3F);
            player.setAllowFlight(true);
            if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.lobby.fly")) {
                player.setFlying(true);
            }
            player.sendMessage("§lJump §l» §cVous avez quitté le jump.");
		});
	}
	
}
