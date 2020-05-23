package fr.sywoo.hub.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;

public class PlayerChat implements Listener {
	@EventHandler
	public void onChat(AsyncPlayerChatEvent event) {
		
		event.setCancelled(true);
		if(event.getMessage() == null) return;
		AccountData data = LionSpigot.get().getAccountManager().get(event.getPlayer().getUniqueId());
		Hub.instance.getChat().sendMessage(event.getPlayer(), data.getPrefix() + event.getPlayer().getName(), event.getMessage());
	}
}
