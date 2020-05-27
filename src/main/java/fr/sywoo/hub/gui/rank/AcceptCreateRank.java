package fr.sywoo.hub.gui.rank;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.sywoo.api.account.AccountData;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.rank.RankEnum;
import fr.sywoo.api.spigot.LionSpigot;

public class AcceptCreateRank extends IQuickInventory {

    private Rank rank;
    private String target;
    private RankEnum rankEnum;

    public AcceptCreateRank(Rank rank, String name, RankEnum rankEnum) {
        super("§aCrée le rank ?", 9);
        this.rank = rank;
        this.target = name;
        this.rankEnum = rankEnum;
    }


    @Override
    public void contents(QuickInventory quickInventory) {
        quickInventory.setItem(new QuickItem(Material.STAINED_CLAY, 1 , (byte) 5).setName("§aChanger le grade").toItemStack(), quickEvent -> {
            Player player = quickEvent.getPlayer();
            
            if(Bukkit.getPlayer(target) != null) {
            	Player to = Bukkit.getPlayer(target);
                to.sendMessage(ChatColor.GREEN + player.getName() + " a modifié ton grade");
            }
            AccountData accountData = LionSpigot.get().getAccountManager().get(target);
            if(accountData == null) {
            	player.sendMessage("§cCe compte n'existe pas !");
            	return;
            }
            rank.getPermissions().clear();
            rank.getPermissions().addAll(rankEnum.getAPermissions());
            LionSpigot.get().getAccountManager().update(accountData.setRank(rank));
            player.sendMessage(ChatColor.GREEN + "Tu as bien modifié le grade de : " + target);
            player.closeInventory();
        }, 3);
        quickInventory.setItem(new QuickItem(Material.PAPER).setName(rank.getName()).setLore(ChatColor.GRAY + "Préfix : " + rank.getDisplayName()).toItemStack(), 4);
        quickInventory.setItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 14).setName("§cAnnuler le changement de grade").toItemStack(), quickEvent -> {
            quickEvent.getPlayer().closeInventory();
        } ,5);
    }
}
