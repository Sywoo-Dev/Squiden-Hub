package fr.sywoo.hub.items;

import org.bukkit.Material;
import org.bukkit.Sound;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.player.JumpPlayer;

public class JumpCheckpointItem extends QuickItem{

	public JumpCheckpointItem() {
		super(Material.SLIME_BALL);
		setName("§a§lCheckpoint").setAction(action -> {
			action.getPlayer().teleport(JumpPlayer.getInfos(action.getPlayer()).getCheckPoint().getCuboid().getCenter());
            action.getPlayer().playSound(action.getPlayer().getLocation(), Sound.NOTE_PLING, 10, 10);
		});	}
	
}
