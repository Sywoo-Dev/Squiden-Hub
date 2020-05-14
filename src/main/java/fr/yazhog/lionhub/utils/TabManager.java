package fr.yazhog.lionhub.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import fr.yazhog.lionapi.account.AccountData;
import fr.yazhog.lionapi.spigot.LionSpigot;

public class TabManager {

    public void reloadTab(){
        for(Player player : Bukkit.getOnlinePlayers()){
            AccountData accountData = LionSpigot.get().getAccountManager().get(player.getUniqueId());
            if(accountData.getRank().hasPermission("*")){
                String nickname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getPrefix();
                String teamname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().getPower() + player.getName();
                if(teamname.length() > 15) {
                	teamname = teamname.substring(teamname.length()-2, teamname.length());
                }
                if(nickname.length() > 16){
                    nickname = nickname.substring(nickname.length()-2, nickname.length());
                }
                TeamsTagsManager.setNameTag(player, teamname, nickname, " §a✔");
            } else {
                String nickname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getPrefix();
                String teamname = LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().getPower() + player.getName();
                if(teamname.length() > 15) {
                	teamname = teamname.substring(teamname.length()-2, teamname.length());
                }
                if(nickname.length() > 16){
                    nickname = nickname.substring(nickname.length()-2, nickname.length());
                }
                TeamsTagsManager.setNameTag(player, teamname, nickname, "");
            }
        }
    }

}
