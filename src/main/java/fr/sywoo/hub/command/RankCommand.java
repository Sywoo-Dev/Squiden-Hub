package fr.sywoo.hub.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.rank.RankMenu;

public class RankCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			 Player player = (Player) sender;
		        if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.rank")){
		            if(args.length == 1){
		                if(args[0] != null){
		                    new RankMenu(args[0], Hub.instance).open(player);
		                } else {
		                    player.sendMessage(ChatColor.RED + "Erreur : Précise un joueur valide");
		                }
		            } else {
		                player.sendMessage(ChatColor.RED + "Erreur : Précise un joueur valide");
		            }
		        } else {
		            sender.sendMessage("§cErreur : Tu n'as pas la permission d'éxécuter cette commande");
		        }
		}
		return false;
	}


}
