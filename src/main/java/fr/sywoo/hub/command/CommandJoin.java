package fr.sywoo.hub.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.queue.Queue;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.spigot.LionSpigot;

public class CommandJoin implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(args.length >= 1) {
				String server = args[0];
				queue(player, LionSpigot.get().getServerDataManager().get(server));
			}
		}
		return false;
	}
	
    public void queue(Player player, ServersData serverData){
        if(!Queue.existFor(serverData.getName())){
            new Queue(serverData.getName());
        }
        Queue queue = Queue.getByName(serverData.getName());
        queue.setServersData(serverData);
        if(queue.getServersData().getOwner().equalsIgnoreCase(player.getName())) {
            LionSpigot.get().getPlayerServerManager().sendPlayerToServer(player.getUniqueId(), queue.getServersData().getName());
            return;
        }
        AccountData data = LionSpigot.get().getAccountManager().get(player.getUniqueId());
        if(queue.getPlayers().contains(data.getRank().getPower() + player.getUniqueId())){
            queue.removePlayer(player);

            player.playSound(player.getLocation(), Sound.VILLAGER_YES, 1, 1);
            player.sendMessage("§7§m-----------------------------------------------------");
            player.sendMessage("§bVous avez quitté la file d'attente !");
            player.sendMessage("§7§m-----------------------------------------------------");
            return;
        }

        queue.addPlayer(data.getRank().getPower() + player.getUniqueId());

        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        player.sendMessage("§7§m-----------------------------------------------------");
        player.sendMessage("§aVous avez été ajouté à la file d'attente pour ce jeu !");
        player.sendMessage("§eVous êtes à la position : §6" + queue.getPosition(data.getRank().getPower() + player.getUniqueId()) + "§e/§6" + queue.getPlayers().size());
        player.sendMessage("§7§m-----------------------------------------------------");
    }

}
