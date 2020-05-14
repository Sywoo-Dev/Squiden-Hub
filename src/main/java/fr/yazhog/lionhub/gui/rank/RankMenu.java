package fr.yazhog.lionhub.gui.rank;

import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionapi.rank.Rank;
import fr.yazhog.lionapi.rank.RankEnum;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionapi.inventory.IQuickInventory;
import fr.yazhog.lionapi.inventory.QuickInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class RankMenu extends IQuickInventory {

    private Player target;
    private Hub hub;

    public RankMenu(Player target, Hub hub) {
        super("§cGrade : " + target.getName(), 9*2);
        this.target = target;
        this.hub = hub;
    }

    @Override
    public void contents(QuickInventory quickInventory) {
        for(RankEnum rankEnum : RankEnum.values()){
            quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1 , (byte) 14).setName(ChatColor.GRAY + rankEnum.getName()).
                    setLore(ChatColor.GRAY + "Préfix : " + rankEnum.getDisplayName(),
                            ChatColor.GRAY + "Power : " + rankEnum.getPower(),
                            ChatColor.GRAY + "Limite d'Host : " + rankEnum.getSLimitHost().replace("-1", "§4Illimité")).toItemStack(), quickEvent -> {
                Rank rank = new Rank(rankEnum);
                rank.addTPermission(rankEnum.getPermissions());
                new AcceptCreateRank(rank, target, hub, rankEnum).open(quickInventory.getOwner());
            });
        }
    }
}
