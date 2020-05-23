package fr.sywoo.hub.gui.rank;

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
import fr.sywoo.hub.Hub;

public class AcceptCreateRank extends IQuickInventory {

    private Rank rank;
    private Player target;
    private Hub hub;
    private RankEnum rankEnum;

    public AcceptCreateRank(Rank rank, Player target, Hub hub, RankEnum rankEnum) {
        super("§aCrée le rank ?", 9);
        this.rank = rank;
        this.target = target;
        this.hub = hub;
        this.rankEnum = rankEnum;
    }


    @Override
    public void contents(QuickInventory quickInventory) {
        quickInventory.setItem(new QuickItem(Material.STAINED_CLAY, 1 , (byte) 5).setName("§aCrée le grade").toItemStack(), quickEvent -> {
            Player player = quickEvent.getPlayer();
            AccountData accountData = LionSpigot.get().getAccountManager().get(target.getUniqueId());
            rank.getPermissions().clear();
            rank.getPermissions().addAll(rankEnum.getAPermissions());
            LionSpigot.get().getAccountManager().update(accountData.setRank(rank));
            hub.getScoreboardManager().update(target);
            player.sendMessage(ChatColor.GREEN + "Tu as bien modifié le grade de : " + target.getName());
            target.sendMessage(ChatColor.GREEN + player.getName() + " a modifié ton grade");
            player.closeInventory();
        }, 3);
        quickInventory.setItem(new QuickItem(Material.PAPER).setName(rank.getName()).setLore(ChatColor.GRAY + "Préfix : " + rank.getDisplayName(),
                ChatColor.GRAY + "Power : " + rank.getPower()).toItemStack(), 4);
        quickInventory.setItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 14).setName("§cAnnuler la création du rank").toItemStack(), quickEvent -> {
            quickEvent.getPlayer().closeInventory();
        } ,5);
    }
}
