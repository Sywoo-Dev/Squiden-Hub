package fr.sywoo.hub.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.spigot.LionSpigot;

public class PermCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;

			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("list")) {
					if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.perm")){
						if(args.length != 2) { player.sendMessage("§cUtilisation : /perm list <player>"); return false; }

						if(Bukkit.getPlayer(args[1]) == null) { player.sendMessage("§cÉrreur : Ce joueur n'existe pas."); return false; }
						Player target = Bukkit.getPlayer(args[1]);

						player.sendMessage("§aVoici la liste des permissions : ");

						for (String str : LionSpigot.get().getAccountManager().get(target.getUniqueId()).getRank().getPermissions()) {
							player.sendMessage("§f - " + str); 
						}

					} else { player.sendMessage("§cErreur : Tu n'as pas la permission d'éxécuter cette commande."); }    }
			}

			if(args.length != 3) {
				sender.sendMessage("§c/perm <add/remove> <pseudo> <permission>");
				return false;
			}

			if(args[0].equalsIgnoreCase("add")) {
				if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.perm")){
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) { player.sendMessage("§cÉrreur : Ce joueur n'existe pas."); return false; }

					AccountData accountData = LionSpigot.get().getAccountManager().get(target.getUniqueId());
					accountData.getRank().addPermission(args[2]);
					LionSpigot.get().getAccountManager().update(accountData);

					player.sendMessage(ChatColor.GREEN + "Tu as bien ajouté la permission : " + args[1] + " à " + target.getName());
					target.sendMessage(ChatColor.GREEN + player.getName() + " t'as ajouté la permission : " + args[2]);

				} else { player.sendMessage("§cErreur : Tu n'as pas la permission d'éxécuter cette commande."); }
			}

			if(args[0].equalsIgnoreCase("remove")) {
				if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.perm")){
					Player target = Bukkit.getPlayer(args[1]);
					if(target == null) { player.sendMessage("§cErreur : Ce joueur n'existe pas."); return false; }
					AccountData accountData = LionSpigot.get().getAccountManager().get(target.getUniqueId());
					accountData.getRank().removePermission(args[2]);
					LionSpigot.get().getAccountManager().update(accountData);

					player.sendMessage(ChatColor.RED + "Tu as bien retiré la permission : " + args[1] + " à " + target.getName());
					target.sendMessage(ChatColor.RED + player.getName() + " t'as retiré la permission : " + args[1]);
				} else {
					player.sendMessage("§cÉrreur : Tu n'as pas la permission d'éxécuter cette commande.");
				}
			}

		}

		return false;
	}


}
