package fr.sywoo.hub.gui.servers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;

public class ServerPlayerGui extends IQuickInventory {

	private Hub hub;

	public ServerPlayerGui(Hub hub) {
		super("§6Crée ton serveur", 9*4);
		this.hub = hub;
	}

	@Override
	public void contents(QuickInventory quickInventory) {
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
		quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 26, 35);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 26);
		quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 35);

		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§6UHC")
				.setLore("", "§bLancez votre propre host UHC afin de profiter un", ChatColor.AQUA + "maximum des customisations de §3Squiden !", "").toItemStack(), quickEvent -> {
					quickEvent.getPlayer().closeInventory();
					if(LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).isHost()){
						new LaunchServerGUI(hub).open(quickEvent.getPlayer());
					} else {
						quickEvent.getPlayer().sendMessage(ChatColor.RED + "Il faut être Host pour pouvoir lancer un serveur à la demande");
						quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
					}
				} ,11);
	}
}
