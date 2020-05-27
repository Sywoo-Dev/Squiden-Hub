package fr.sywoo.hub.gui.rank;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.rank.Rank;
import fr.sywoo.api.rank.RankEnum;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;

public class RankMenu extends IQuickInventory {

	private String target;

	public RankMenu(String target, Hub hub) {
		super("§cGrade : " + target, 9*2);
		this.target = target;
	}

	@Override
	public void contents(QuickInventory quickInventory) {
		for(RankEnum rankEnum : RankEnum.values()){
			quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1 , (byte) 14).setName(ChatColor.GRAY + rankEnum.getName()).
					setLore(ChatColor.GRAY + "Préfix : " + rankEnum.getDisplayName(),
							ChatColor.GRAY + "Power : " + rankEnum.getPower()).toItemStack(), quickEvent -> {
								Rank rank = new Rank(rankEnum);
								rank.addTPermission(rankEnum.getPermissions());
								new AcceptCreateRank(rank, target.toLowerCase(), rankEnum).open(quickInventory.getOwner());
							});
		}
		
		quickInventory.setItem(new QuickItem(Material.PAPER).setName(target).setLore("Grade actuel : " + LionSpigot.get().getAccountManager().get(target).getRank().getName()).toItemStack(), 17);
	}
}
