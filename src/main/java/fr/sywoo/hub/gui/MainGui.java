package fr.sywoo.hub.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.queue.Queue;
import fr.sywoo.api.serverdata.GameType;
import fr.sywoo.api.serverdata.ServerStatus;
import fr.sywoo.api.serverdata.ServersData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.servers.ArenaGui;
import fr.sywoo.hub.gui.servers.LobbyGui;
import fr.sywoo.hub.gui.servers.ServerPlayerGui;
import fr.sywoo.hub.gui.servers.UHCGui;
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
					quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
					quickEvent.getPlayer().sendMessage("§cCette fonctionnalité n'est pas encore disponnible");
				}, 50);
		quickInventory.setItem(new QuickItem(Material.BEACON).setName("§eSélécteur de Lobby").setLore("", "§8» §eCliquez pour y accéder").toItemStack(), quickEvent -> {
			new LobbyGui(hub).open(quickEvent.getPlayer());
			quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.LEVEL_UP, 10 ,10);
		}, 53);
		hub.getPlayerUtils();
		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§3UHC §d§k|§r §6§lHOSTS §d§k|")
				.setLore("", ChatColor.GREEN + "" + ChatColor.BOLD + "Seul §7ou en §cé§lquipe §7affrontez des", ChatColor.GRAY + "joueurs sur une map en difficulté hardcore.",
						"", "§bJoueurs en jeux §f: §c" + PlayerUtils.getUhcPlayers(), "", "§a» §eCliquez ici pour jouer").toItemStack(), quickEvent -> {
							new UHCGui().open(quickInventory.getOwner());
						},21);


		hub.getPlayerUtils();
		quickInventory.setItem(new QuickItem(Material.BEACON).setName("§bKaptur §d§k|§r §e§lINEDIT §d§k|") //§e§lINEDIT
				.setLore("", "§b§lCapturer §7et §c§lTuer", "§7seront les mots d'ordres", "§7Dans ce mode de jeu §e§lInédit", "§7vous vous devez d'être §a§lStratégique §7!", "",
						"§bJoueurs en jeux §f: §c" + PlayerUtils.getKapturPlayers(), "",
						"§a» §eCliquez ici pour jouer").toItemStack(), quickEvent -> {

							//if(!LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).getRank().hasPermission("moderation.tool")) return;
							if(LionSpigot.get().getServerManager().getKaptur().size() == 0) {
								String name = LionSpigot.get().getServerManager().createAndGetServerName("Kaptur");
								LionSpigot.get().getServerDataManager().create(new ServersData("LionUhc", name, ServerStatus.WAITING, GameType.KAPTUR));
								quickEvent.getPlayer().sendMessage("§a§lUn Serveur est en cours de lancement...");
							}
							if(!Queue.existFor("Kaptur")) {
								new Queue("Kaptur", GameType.KAPTUR);
							}
							Queue.getByName("Kaptur").addPlayer(quickEvent.getPlayer().getUniqueId());
							quickEvent.getPlayer().closeInventory();

						}, 23);

		quickInventory.setItem(new QuickItem(Material.FEATHER).setName("§bSkywars §d§k|§r §e§lBêta §d§k|") //§6§lPOPULAIRE
				.setLore("", "§7Le Skywars classique", "§7Revisité par §6Lion§cUhc", "", "§bJoueurs en jeux §f: §c" + PlayerUtils.getSkywarsPlayers(), "", "§a» §eCliquez ici pour jouer").toItemStack(), quickEvent -> {

					//if(!LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).getRank().hasPermission("moderation.tool")) return;
					if(LionSpigot.get().getServerManager().getSkywarsServers().size() == 0) {
						String name = LionSpigot.get().getServerManager().createAndGetServerName("Skywars");
						LionSpigot.get().getServerDataManager().create(new ServersData("LionUhc", name, ServerStatus.WAITING, GameType.SKYWARS));
						quickEvent.getPlayer().sendMessage("§a§lUn Serveur est en cours de lancement...");
					}
					if(!Queue.existFor("Skywars")) {
						new Queue("Skywars", GameType.SKYWARS);
					}
					Queue.getByName("Skywars").addPlayer(quickEvent.getPlayer().getUniqueId());
					quickEvent.getPlayer().closeInventory();

				}, 30);
		
		quickInventory.setItem(new QuickItem(Material.BEDROCK).setName("§eGolemRush §d§k|§r §c§lSOON §d§k|") //§6§lPOPULAIRE
				.setLore("", "§7Jeu En équipe", "§7Protègez votre Golem", "", "§cBientôt"/*"§bJoueurs en jeux §f: §c" + PlayerUtils.getSkywarsPlayers(), "", "§a» §eCliquez ici pour jouer"*/).toItemStack(), quickEvent -> {

					

				}, 31);
		
		quickInventory.setItem(new QuickItem(Material.BEDROCK).setName("§6UhcRun §d§k|§r §c§lSOON §d§k|") //§6§lPOPULAIRE
				.setLore("", "§7Uhc avec une phase de préparation", "§7Ultra rapide et une génération modifiée", "", "§cBientôt" /*"§bJoueurs en jeux §f: §c" + PlayerUtils.getSkywarsPlayers(), "", "§a» §eCliquez ici pour jouer"*/).toItemStack(), quickEvent -> {

					

				}, 32);

		quickInventory.setItem(new QuickItem(Material.DIAMOND_AXE).setName("§bArena §d§k|§r §a§lNew Map §d§k|§r")
				.setLore("", "§c§lBattez vous §7sans relâche", "§7dans une map prévu a cet effet.", "§a§lBonne chance §c!",
						"", "§bJoueurs en jeux §7: §c" + hub.getPlayerUtils().getArenaPlayers(), "", "§a» §eCliquez ici pour jouer", "").toItemStack(), quickEvent -> {
							new ArenaGui().open(quickInventory.getOwner());
						}, 22);
		quickInventory.setItem(new QuickItem(Material.COMMAND_MINECART).setName("§6Serveurs à la demande")
				.setLore("", "§7Cliquez ici pour créer un serveur", "", "§6Créer ton propre serveur de jeux personnalisés !",
						"", "§c⚠Accéssible pour les joueurs possédant", "§cau minimum le grade §8Bronze")
				.toItemStack(), quickEvent -> {
					Player player = quickEvent.getPlayer();
//					if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.servers.start")){
						new ServerPlayerGui(hub).open(player);
						player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
//					} else {
//						player.closeInventory();
//						player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 10, 10);
//						player.sendMessage("§cÉrreur : Vous n'avez pas la permission d'utiliser cette fonctionnalitée");
//					}
				}, 8);
	}
}
