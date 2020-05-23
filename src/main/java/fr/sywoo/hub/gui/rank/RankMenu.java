package fr.sywoo.hub.gui.rank;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.rank.RankEnum;
import fr.sywoo.hub.Hub;

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
							ChatColor.GRAY + "Power : " + rankEnum.getPower()).toItemStack(), quickEvent -> {
								Rank rank = new Rank(rankEnum);
								rank.addTPermission(rankEnum.getPermissions());
								new AcceptCreateRank(rank, target, hub, rankEnum).open(quickInventory.getOwner());
							});
		}
	}
}
