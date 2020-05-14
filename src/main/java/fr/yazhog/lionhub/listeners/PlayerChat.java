package fr.yazhog.lionhub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.yazhog.lionapi.account.AccountData;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionhub.Hub;

public class PlayerChat implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		event.setCancelled(true);
		if(event.getMessage() == null) return;
		AccountData data = LionSpigot.get().getAccountManager().get(event.getPlayer().getUniqueId());
		Hub.instance.getChat().sendMessage(event.getPlayer(), data.getPrefix() + event.getPlayer().getName(), event.getMessage());
	}
}
