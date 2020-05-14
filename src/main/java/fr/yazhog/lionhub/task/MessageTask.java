package fr.yazhog.lionhub.task;

import fr.yazhog.lionapi.utils.TextComponentBuilder;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
			}
		} else if(min == 20){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.sendMessage("");
				player.spigot().sendMessage(new TextComponentBuilder("§7■ §fEnvie de devenir §e§lHOST§f?" +
						"\n§b➤ N'hesitez plus et allez sur notre shop afin de trouver ce qu'il vous faut !")
						.setClickEvent(ClickEvent.Action.OPEN_URL, "https://lionuhc.eu/shop").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "§5BOUTIQUE").toText());
				player.sendMessage("");
			}
		} else if(min == 30){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.sendMessage("");
				player.sendMessage("§7■ §cUtiliser un moyen de faire beaucoup de CPS est §lextrêmement §r§cdécouragé.");
				player.sendMessage("");
			}
		} else if(min == 40){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.sendMessage("");
				player.sendMessage("§7■ §fNOUVEAUX MINI-JEUX ➤ §e§lKAPTUR §fET §d§lSKYWARS §a§lDISPONNIBLE !");
				player.sendMessage("");
			}
		} else if(min == 50){
			for(Player player : Bukkit.getOnlinePlayers()){
				player.sendMessage("");
				player.spigot().sendMessage(new TextComponentBuilder("§7■ §bVous souhaitez faire parti du STAFF ?" +
						"\n§b➤ N'hesitez plus et allez §ltenter votre chance !")
						.setClickEvent(ClickEvent.Action.OPEN_URL, "https://docs.google.com/forms/d/e/1FAIpQLSckca_4HadPP7mwQ3ZW8QvizDxslz_ZilOUp7oR9eD-OZCmeA/viewform").setHoverEvent(HoverEvent.Action.SHOW_TEXT, "§bRECRUTEMENTS").toText());
				player.sendMessage("");
				min = 0;
			}
		}
	}
}
