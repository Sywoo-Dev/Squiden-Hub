package fr.yazhog.lionhub.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.yazhog.lionhub.player.JumpPlayer;
import fr.yazhog.lionhub.utils.Location;

public class JumpCommand implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
        Player player = (Player) sender;
        if(JumpPlayer.getInfos(player) == null){
            player.teleport(new Location(63.5, 16.0, -58.5).setYaw(-90).setPitch(0).getAsLocation());
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
        }		return false;
	}
}
