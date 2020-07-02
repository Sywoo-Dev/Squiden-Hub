package fr.sywoo.hub.gui.settings;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.settings.Settings;
import fr.sywoo.api.settings.Settings.SettingsEnum;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.MainGui;

public class SettingsGui extends IQuickInventory {

	private Hub hub;

	public SettingsGui(Hub hub) {
		super("§6Settings", 9*5);
		this.hub = hub;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void contents(QuickInventory quickInventory) {
		AccountData account = LionSpigot.get().getAccountManager().get(quickInventory.getOwner().getUniqueId());
		quickInventory.updateItem("settings", taskUpdate -> {

			quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getFriend().getItemStack()),
					1, DyeColor.valueOf(account.getSettings().getFriend().getDyeColor()).getData()).setName("§bDemande d'ami")
					.setLore("§7Actuellement : " + account.getSettings().getFriend().getName()).toItemStack(), quickEvent -> {
						LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setFriend(allow(account.getSettings().getFriend()))));
					}, 11);
			quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getAutoReconnect().getItemStack()),
					1, DyeColor.valueOf(account.getSettings().getAutoReconnect().getDyeColor()).getData()).setName("§bAuto-Reconnexion")
					.setLore("§7Actuellement : " + account.getSettings().getAutoReconnect().getName()).toItemStack(), quickEvent -> {
						LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setAutoRecconnect(allow(account.getSettings().getAutoReconnect()))));
					}, 12);

			quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getMessage().getItemStack()),
					1, DyeColor.valueOf(account.getSettings().getMessage().getDyeColor()).getData()).setName("§bGestion des messages privés")
					.setLore("§7Actuellement : " + account.getSettings().getMessage().getName()).toItemStack(), quickEvent -> {
						LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setMessage(toggle(account.getSettings().getMessage()))));
					}, 13);

			quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getSeeChat().getItemStack()),
					1, DyeColor.valueOf(account.getSettings().getSeeChat().getDyeColor()).getData()).setName("§bGestion du chat (Lobby)")
					.setLore("§7Actuellement : " + account.getSettings().getSeeChat().getName()).toItemStack(), quickEvent -> {
						LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setSeeChat(toggle(account.getSettings().getSeeChat()))));
					}, 14);

			quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getSeePlayers().getItemStack()),
					1, DyeColor.valueOf(account.getSettings().getSeePlayers().getDyeColor()).getData()).setName("§bParticules et Joueurs")
					.setLore("§7Actuellement : " + account.getSettings().getSeePlayers().getName()).toItemStack(), quickEvent -> {
						LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setSeePlayers(allow(account.getSettings().getSeePlayers()))));
						if(LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).getSettings().getSeePlayers() == SettingsEnum.DISALLOW) {
							for(Player players : Bukkit.getOnlinePlayers()) {
								quickEvent.getPlayer().hidePlayer(players);
							}
						}else {
							for(Player players : Bukkit.getOnlinePlayers()) {
								if(!LionSpigot.get().getAccountManager().get(players.getUniqueId()).isVanished()) {
									quickEvent.getPlayer().showPlayer(players);
								}
							}
						}
					}, 15);

//			quickInventory.setItem(new QuickItem(Material.IRON_SWORD).setName("§CParticule de Mort").toItemStack(), quickEvent -> {
//				Bukkit.dispatchCommand(quickEvent.getPlayer(), "deathparticle");
//			}, 20);
//
//
//			quickInventory.setItem(new QuickItem(Material.DIAMOND_SWORD).setName("§CParticule de Kill").toItemStack(), quickEvent -> {
//				quickEvent.getPlayer().sendMessage("§cSOON");
//			}, 24);

		}, 1);

		quickInventory.setItem(new QuickItem(Material.BARRIER).setName("§c§lFermer").toItemStack(), quickEvent -> {
			quickEvent.getPlayer().closeInventory();
		}, 44);

		quickInventory.setItem(new QuickItem(Material.ARROW).setName("§cRetour").toItemStack(), quickEvent -> {
			new MainGui(hub).open(quickEvent.getPlayer());
		}, 0);
	}

	private Settings.SettingsEnum allow(Settings.SettingsEnum settingsEnum){
		switch (settingsEnum){
		case ALLOW:
			return Settings.SettingsEnum.DISALLOW;
		case DISALLOW:
			return Settings.SettingsEnum.ALLOW;
		default:
			break;
		}
		return null;
	}

	private Settings.SettingsEnum toggle(Settings.SettingsEnum settingsEnum){
		switch (settingsEnum){
		case ALLOW:
			return Settings.SettingsEnum.FRIENDS;
		case FRIENDS:
			return Settings.SettingsEnum.DISALLOW;
		case DISALLOW:
			return Settings.SettingsEnum.ALLOW;
		}
		return null;
	}

}
