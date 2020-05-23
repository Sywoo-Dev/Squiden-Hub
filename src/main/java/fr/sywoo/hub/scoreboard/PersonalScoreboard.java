package fr.sywoo.hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.guilds.Guilds;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.utils.QuickString;

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
		AccountData data = LionSpigot.get().getAccountManager().get(player.getUniqueId());
		Rank rank = data.getRank();
		objectiveSign.setDisplayName("§c§lLion§6§lUHC");

		objectiveSign.setLine(1, "§f§l    ❝  Compte ❞");
		objectiveSign.setLine(2, "§7» §fGrade : " + rank.getColor() + rank.getName());
		objectiveSign.setLine(3, "§7» §fCoins : §e" + data.getCoins());
		objectiveSign.setLine(4, "§7» §fShishi : §e" + data.getShiShi());
		objectiveSign.setLine(5, "§2");
				
		objectiveSign.setLine(5, "§f§l    ❝  Guilde ❞");
		if (data.getGuildsName() != null) {
			Guilds guild = LionSpigot.get().getGuildsManager().get(data.getGuildsName());
			if(guild != null) {
				objectiveSign.setLine(6, "§7» §fNom : §b" + guild.getName());
				objectiveSign.setLine(7, "§7» §fRole : " + ChatColor.translateAlternateColorCodes('&',	data.getGuildRank()));
				objectiveSign.setLine(8, "§7» §fNiveau: §a" + guild.getLevel());
				objectiveSign.setLine(9, "  " + new QuickString().getProgressBar((int) guild.getXP(), (int) guild.getLevel()*5000, 12, '❚', ChatColor.YELLOW, ChatColor.RED));
			}else {
				objectiveSign.setLine(6, "§7» §fNom : §bAucune");
				objectiveSign.setLine(7, "§7» §fRole : §bAucun");
			}
		}else {
			objectiveSign.setLine(6, "§7» §fNom : §bAucune");
			objectiveSign.setLine(7, "§7» §fRole : §bAucun");
		}
		objectiveSign.setLine(8, "§3");
		objectiveSign.setLine(9, ip);	

		objectiveSign.updateLines();
	}

	public void onLogout(){
		objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(player.getUniqueId()));
	}
}