package fr.yazhog.lionhub.gui.settings;

import fr.yazhog.lionapi.account.AccountData;
import fr.yazhog.lionapi.inventory.IQuickInventory;
import fr.yazhog.lionapi.inventory.QuickInventory;
import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionapi.settings.Settings;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.gui.MainGui;
import org.bukkit.DyeColor;
import org.bukkit.Material;

public class SettingsGui extends IQuickInventory {

    private Hub hub;

    public SettingsGui(Hub hub) {
        super("§6Settings", 9*5);
        this.hub = hub;
    }

    @Override
    public void contents(QuickInventory quickInventory) {
        AccountData account = LionSpigot.get().getAccountManager().get(quickInventory.getOwner().getUniqueId());
        quickInventory.updateItem("settings", taskUpdate -> {
            quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getFriend().getItemStack()),
                    1, DyeColor.valueOf(account.getSettings().getFriend().getDyeColor()).getData()).setName("§bGestion des amis")
                    .setLore("§7Actuellement : " + account.getSettings().getFriend().getName()).toItemStack(), quickEvent -> {
                switch (account.getSettings().getFriend()){
                    case ALLOW:
                        account.getSettings().setFriend(Settings.SettingsEnum.DISALLOW);
                        break;
                    case DISALLOW:
                        account.getSettings().setFriend(Settings.SettingsEnum.ALLOW);
                        break;
                }
                LionSpigot.get().getAccountManager().update(account);
            }, 10);
            quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getMessage().getItemStack()),
                    1, DyeColor.valueOf(account.getSettings().getMessage().getDyeColor()).getData()).setName("§bGestion des messages privés")
                    .setLore("§7Actuellement : " + account.getSettings().getMessage().getName()).toItemStack(), quickEvent -> {
                LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setMessage(toggle(account.getSettings().getMessage()))));
            }, 13);
            quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getParty().getItemStack()),
                    1, DyeColor.valueOf(account.getSettings().getParty().getDyeColor()).getData()).setName("§bGestion des demande de party")
                    .setLore("§7Actuellement : " + account.getSettings().getParty().getName()).toItemStack(), quickEvent -> {
                LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setParty(toggle(account.getSettings().getParty()))));
            }, 16);
            quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getSeeChat().getItemStack()),
                    1, DyeColor.valueOf(account.getSettings().getSeeChat().getDyeColor()).getData()).setName("§bGestion du chat (Lobby)")
                    .setLore("§7Actuellement : " + account.getSettings().getSeeChat().getName()).toItemStack(), quickEvent -> {
                LionSpigot.get().getAccountManager().update(account.setSettings(account.getSettings().setSeeChat(toggle(account.getSettings().getSeeChat()))));
            }, 29);
            quickInventory.setItem(new QuickItem(Material.valueOf(account.getSettings().getShopOptions().getItemStack()),
                    1, DyeColor.valueOf(account.getSettings().getShopOptions().getDyeColor()).getData()).setName("§bGestion des achats")
                    .setLore("§7Actuellement : " + account.getSettings().getShopOptions().getName()).toItemStack(), quickEvent -> {
                switch (account.getSettings().getShopOptions()){
                    case ALLOW:
                        account.getSettings().setShopOptions(Settings.SettingsEnum.DISALLOW);
                        break;
                    case DISALLOW:
                        account.getSettings().setShopOptions(Settings.SettingsEnum.ALLOW);
                        break;
                }
                LionSpigot.get().getAccountManager().update(account);
            }, 33);
        });
        quickInventory.setItem(new QuickItem(Material.BARRIER).setName("§c§lFermer").toItemStack(), quickEvent -> {
            quickEvent.getPlayer().closeInventory();
        }, 44);
        quickInventory.setItem(new QuickItem(Material.ARROW).setName("§cRetour").toItemStack(), quickEvent -> {
            new MainGui(hub).open(quickEvent.getPlayer());
        }, 0);
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
