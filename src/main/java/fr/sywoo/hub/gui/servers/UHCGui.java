package fr.sywoo.hub.gui.servers;

import org.bukkit.Material;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.gui.servers.uhcs.ClassicGUI;
import fr.sywoo.hub.gui.servers.uhcs.LgGUI;
import fr.sywoo.hub.gui.servers.uhcs.TaupeGUI;

public class UHCGui extends IQuickInventory {

    public UHCGui() {
        super("§aChoisis une catégorie UHC", 9);
    }

	@Override
    public void contents(QuickInventory quickInventory) {
		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§6§lClassic§a§l/§e§lMeetup").toItemStack(), onClick -> {
			new ClassicGUI().open(onClick.getPlayer());
		}, 2);
		
		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§7Loup-Garou").toItemStack(), onClick -> {
			new LgGUI().open(onClick.getPlayer());
		}, 4);
		
		quickInventory.setItem(new QuickItem(Material.GOLDEN_APPLE).setName("§3Taupe-Gun").toItemStack(), onClick -> {
			new TaupeGUI().open(onClick.getPlayer());
		}, 6);
    }

}