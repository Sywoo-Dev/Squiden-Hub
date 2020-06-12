package fr.sywoo.hub.gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.enums.Games;
import fr.sywoo.hub.gui.servers.LobbyGui;
import fr.sywoo.hub.gui.servers.ServerPlayerGui;
import fr.sywoo.hub.gui.settings.SettingsGui;
import fr.sywoo.hub.utils.PlayerUtils;

public class MainGui extends IQuickInventory {

	private Hub hub;

	public MainGui(Hub hub) {
		super("§6Séléction de serveurs", 9*6);
		this.hub = hub;
	}

	@Override
	public void contents(QuickInventory quickInventory) {
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 45, 53);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 45);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 53);
		hub.getPlayerUtils();

		for(Games games : Games.values()) {
			if(games == Games.ARENAKIT) continue;
			List<String> lores = new ArrayList<String>(Arrays.asList(games.getDescription()));
			lores.add("§2");
			if(games == Games.ARENA) {
				lores.add("§bJoueurs en jeux §f: §c" + (PlayerUtils.getGamePlayer(games.getGroup()) + PlayerUtils.getGamePlayer("ArenaKit")));
			}else if(games == Games.HIKABRAIN) {
				lores.add("§bJoueurs en jeux §f: §c" + (PlayerUtils.getGamePlayer(games.getGroup()) + PlayerUtils.getGamePlayer("HikaTO2") + PlayerUtils.getGamePlayer("HikaTO4")));
			}else {
				lores.add("§bJoueurs en jeux §f: §c" + PlayerUtils.getGamePlayer(games.getGroup()));
			}
			lores.add("§6Développeur : §e" + games.getDevelopper());
			lores.add("§3");
			if(Hub.instance.maintaining.contains(games)){
				lores.add("§c» §6Jeu en Maintenance.");
			}else {
				lores.add("§a» §eCliquez ici pour jouer");
			}

			quickInventory.setItem(new QuickItem(games.getIcon()).setName("§3" + games.getName() + " " + games.getAnnotation())
					.setLore(lores).toItemStack(), onClick -> {
						if(games.getInventory() != null) {
							games.getInventory().open(onClick.getPlayer());
						}else {
							if(!Hub.instance.maintaining.contains(games)) {
								LionSpigot.get().addPlayerInWaitingQueue(onClick.getPlayer(), games.getGroup());
								onClick.getPlayer().closeInventory();
							}else {
								onClick.getPlayer().sendMessage("§cCe jeu est en maintenance !");
							}
						}
					}, games.getSlot());
		}


		quickInventory.setItem(new QuickItem(Material.SKULL_ITEM, 1, (byte) 3).setName(ChatColor.RED + quickInventory.getOwner().getName())
				.setLore("§7Grade : " + LionSpigot.get().getAccountManager().get(quickInventory.getOwner().getUniqueId()).getRank().getName(),
						"§7Coins : " + LionSpigot.get().getAccountManager().get(quickInventory.getOwner().getUniqueId()).getCoins())
				.setSkullOwner(quickInventory.getOwner().getName())
				.toItemStack(),45);

		quickInventory.setItem(new QuickItem(Material.FEATHER).setName("§eJump")
				.setLore("§bJump : Réalisez le meilleur temps et", "essayez de finir 1er du classement !").toItemStack(), quickEvent -> {
					Bukkit.dispatchCommand(quickEvent.getPlayer(), "jump");
				}, 48);

		quickInventory.setItem(new QuickItem(Material.REDSTONE_COMPARATOR).setName("§cParamètres")
				.setLore("", "§cCette fonctionnalitée arrivera plus tard").toItemStack(), quickEvent -> {
					new SettingsGui(hub).open(quickEvent.getPlayer());
				}, 50);

		quickInventory.setItem(new QuickItem(Material.BEACON).setName("§eSélécteur de Lobby").setLore("", "§8» §eCliquez pour y accéder").toItemStack(), quickEvent -> {
			new LobbyGui(hub).open(quickEvent.getPlayer());
			quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.LEVEL_UP, 10 ,10);
		}, 53);

		quickInventory.setItem(new QuickItem(Material.COMMAND_MINECART).setName("§6Serveurs à la demande")
				.setLore("", "§7Cliquez ici pour créer un serveur", "", "§6Créer ton propre serveur de jeux personnalisés !",
						"", "§c⚠Accéssible pour les joueurs possédant", "§cLe §eHost §c!")
				.toItemStack(), quickEvent -> {
					Player player = quickEvent.getPlayer();
					if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).isHost()){
						new ServerPlayerGui(hub).open(player);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
					} else {
						player.closeInventory();
						player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10, 10);
						player.sendMessage("§cÉrreur : Vous n'avez pas la permission d'utiliser cette fonctionnalitée");
					}
				}, 8);
	}
}
