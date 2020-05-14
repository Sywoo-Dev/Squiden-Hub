package fr.yazhog.lionhub.scoreboard;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.yazhog.lionapi.guilds.Guilds;
import fr.yazhog.lionapi.rank.Rank;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionhub.utils.QuickString;

public class PersonalScoreboard {

	private Player player;
	private final ObjectiveSign objectiveSign;

	PersonalScoreboard(Player player){
		this.player = player;
		objectiveSign = new ObjectiveSign("sidebar", "LionUHC");
		reloadData();
		objectiveSign.addReceiver(player);

	}

	public void reloadData(){
	}

	public void setLines(String ip){
		Rank rank = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank();
		objectiveSign.setDisplayName("§c§lLion§6§lUHC");

		objectiveSign.setLine(1, "§f§l    ❝  Compte ❞");
		objectiveSign.setLine(2, "§7» §fCoins : §e" + LionSpigot.get().getAccountManager().get(player.getUniqueId()).getCoins());
		objectiveSign.setLine(3, "§7» §fShishi : §e" + LionSpigot.get().getAccountManager().get(player.getUniqueId()).getShiShi());
		objectiveSign.setLine(4, "§2");

		objectiveSign.setLine(5, "§f§l    ❝ Hosts ❞");
		if(rank.getLimitHost() != 0) {
			objectiveSign.setLine(6, "§7» §fHost : §6" + rank.getSLimitHost().replace("-1", "§4Illimité").replace("-2", "§4Illimité"));
		}else {
			objectiveSign.setLine(6, "§7» §fHost : §cAucun");
		}
		
		if (rank.getExpire() != null) {
			if (rank.getExpire().after(new Date())) {
				objectiveSign.setLine(7, "§7» §fExpiration: §c" + new SimpleDateFormat("dd/MM").format(rank.getExpire()));
			} else {
				objectiveSign.setLine(7, "§7» §fSouscire: §c/store");
			} 
		}else {
			objectiveSign.setLine(7, "§7» §fExpiration: §aJamais");
		}
		
		objectiveSign.setLine(8, "§b");
		
		objectiveSign.setLine(9, "§f§l    ❝  Guilde ❞");
		if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getGuildsName() != null) {
			Guilds guild = LionSpigot.get().getGuildsManager().get(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getGuildsName());
			if(guild != null) {
				objectiveSign.setLine(10, "§7» §fNom : §b" + guild.getName());
				objectiveSign.setLine(11, "§7» §fRole : " + ChatColor.translateAlternateColorCodes('&',	LionSpigot.get().getAccountManager().get(player.getUniqueId()).getGuildRank()));
				objectiveSign.setLine(12, "§7» §cLevel: §f" + guild.getLevel());
				objectiveSign.setLine(13, "  " + new QuickString().getProgressBar((int) guild.getXP(), (int) guild.getLevel()*5000, 12, '❚', ChatColor.YELLOW, ChatColor.RED));
			}else {
				objectiveSign.setLine(10, "§7» §fNom : §bAucune");
				objectiveSign.setLine(11, "§7» §fRole : §bAucun");
			}
		}else {
			objectiveSign.setLine(10, "§7» §fNom : §bAucune");
			objectiveSign.setLine(11, "§7» §fRole : §bAucun");
		}
		objectiveSign.setLine(14, "§3");
		objectiveSign.setLine(15, ip);	

		objectiveSign.updateLines();
	}

	public void onLogout(){
		objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(player.getUniqueId()));
	}
}