package fr.sywoo.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.spigot.LionSpigot;

public class TabManager {

    public void reloadTab(){
        for(Player player : Bukkit.getOnlinePlayers()){
        	
            AccountData accountData = LionSpigot.get().getAccountManager().get(player.getUniqueId());
            String nickname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getPrefix();
            String teamname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().getPower() + player.getName();
            if(teamname.length() > 15) {
            	teamname = teamname.substring(teamname.length()-2, teamname.length());
            }
            if(nickname.length() > 16){
                nickname = nickname.substring(nickname.length()-2, nickname.length());
            }
            
            if(accountData.getRank().hasPermission("*")){
                TeamsTagsManager.setNameTag(player, teamname, nickname, " §a✔");
            }else if(accountData.isHost()){
                TeamsTagsManager.setNameTag(player, teamname, nickname, " §e✯");
            }else {
                TeamsTagsManager.setNameTag(player, teamname, nickname, "");
            }
        }
    }

}
