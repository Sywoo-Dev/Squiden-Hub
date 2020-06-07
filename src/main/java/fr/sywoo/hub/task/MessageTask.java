package fr.sywoo.hub.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.api.utils.TextComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;

public class MessageTask extends BukkitRunnable {

	private int min = 0;

	@Override
	public void run() {
		min++;
		if(min == 10){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.sendMessage("");
				player.spigot().sendMessage(new TextComponentBuilder("§7■ §bRejoignez nous sur discord !")
						.setClickEvent(ClickEvent.Action.OPEN_URL, "https://discord.gg/jph5PhY").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "§bRejoins notre discord !").toText());
				player.sendMessage("");
				min = 0;
			}
		}
	}
}
