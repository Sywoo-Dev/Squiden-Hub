package fr.sywoo.hub.items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.player.JumpPlayer;

public class JumpCheckpointItem extends QuickItem{

	public JumpCheckpointItem() {
		super(Material.SLIME_BALL);
		setName("§a§lCheckpoint").setAction(action -> {
			Location loc = JumpPlayer.getInfos(action.getPlayer()).getCheckPoint().getCuboid().getCenter();
			loc.setYaw(JumpPlayer.getInfos(action.getPlayer()).getCheckPoint().getYaw());
			action.getPlayer().teleport(loc);
            action.getPlayer().playSound(action.getPlayer().getLocation(), Sound.NOTE_PLING, 10, 10);
		});	}
	
}
