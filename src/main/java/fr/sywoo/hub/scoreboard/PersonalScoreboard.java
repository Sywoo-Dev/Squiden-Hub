package fr.sywoo.hub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.spigot.LionSpigot;

public class PersonalScoreboard {

	private Player player;
	private final ObjectiveSign objectiveSign;

	PersonalScoreboard(Player player){
		this.player = player;
		objectiveSign = new ObjectiveSign("sidebar", "§b§l" + LionSpigot.get().getProjectDisplayName());
		reloadData();
		objectiveSign.addReceiver(player);

	}

	public void reloadData(){
	}

	public void setLines(String ip){
		AccountData data = LionSpigot.get().getAccountManager().get(player.getUniqueId());
		Rank rank = data.getRank();
		
        objectiveSign.setLine(0, "§2");
        objectiveSign.setLine(1, "§7»    §6Compte");
        objectiveSign.setLine(2, "§7» §6Grade : " + rank.getName());
        objectiveSign.setLine(3, "§7» §e⛃  " + data.getCoins());
        objectiveSign.setLine(4, "§7» §b🦑 " + data.getShiShi());
        objectiveSign.setLine(5, "§7");
        objectiveSign.setLine(6, "§7»    §2Guilde");
        objectiveSign.setLine(7, "§7» §aGuilde : " + data.getGuildsName());
        objectiveSign.setLine(8, "§7» §aPoste : " + ChatColor.translateAlternateColorCodes('&', data.getGuildRank()));
        objectiveSign.setLine(9, "§3");
        objectiveSign.setLine(10, "§7» §3Lobby §c#" + Bukkit.getServerName().split("-")[1]);
        objectiveSign.setLine(11, "§7» §3Connectés : " + Bukkit.getOnlinePlayers().size());
        objectiveSign.setLine(12, "§4");
        objectiveSign.setLine(13, ip);

		objectiveSign.updateLines();
	}

	public void onLogout(){
		objectiveSign.removeReceiver(Bukkit.getServer().getOfflinePlayer(player.getUniqueId()));
	}
}