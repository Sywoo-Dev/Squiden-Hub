package fr.yazhog.lionhub.gui.rank;

import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionapi.rank.Rank;
import fr.yazhog.lionapi.rank.RankEnum;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionapi.account.AccountData;
import fr.yazhog.lionapi.inventory.IQuickInventory;
import fr.yazhog.lionapi.inventory.QuickInventory;
import fr.yazhog.lionapi.spigot.LionSpigot;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

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
                ChatColor.GRAY + "Power : " + rank.getPower(),
                ChatColor.GRAY + "Limite d'Host : " + rank.getLimitHost()).toItemStack(), 4);
        quickInventory.setItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 14).setName("§cAnnuler la création du rank").toItemStack(), quickEvent -> {
            quickEvent.getPlayer().closeInventory();
        } ,5);
    }
}
