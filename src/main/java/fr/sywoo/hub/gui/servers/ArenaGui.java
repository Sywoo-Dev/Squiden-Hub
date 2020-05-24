package fr.sywoo.hub.gui.servers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.ServiceInfoSnapshotUtil;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;

public class ArenaGui extends IQuickInventory {

    public ArenaGui() {
        super("§aChoisis un serveur Arena", 9*5);
    }

    @SuppressWarnings("deprecation")
	@Override
    public void contents(QuickInventory quickInventory) {
        quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
        quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 35, 44);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 35);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 44);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).toItemStack(), 10, 28);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).toItemStack(), 16, 34);
        CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
                .filter(serviceInfoSnapshot -> serviceInfoSnapshot.getServiceId().getName().startsWith("Arena-"))
                .forEach(serviceInfoSnapshot -> {
                    String arena = serviceInfoSnapshot.getServiceId().getName().substring(0, 5); String number = serviceInfoSnapshot.getServiceId().getName().substring(6);
                    String name = ChatColor.DARK_GRAY + arena + " §b#" + number;
                    
                    quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 5)
                            .setName(name + " §7(" + ServiceInfoSnapshotUtil.getPlayers(serviceInfoSnapshot).size() + " joueurs)")
                            .setLore("" , "§8⤷ §eClique ici pour rejoindre")
                            .addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 65)
                            .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                            .toItemStack(), quickEvent -> {
                        quickEvent.getPlayer().sendMessage("§eConnexion à §a" + serviceInfoSnapshot.getServiceId().getName());
                        quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 10);
                        BridgePlayerManager.getInstance().proxySendPlayer(quickEvent.getPlayer().getUniqueId(), serviceInfoSnapshot.getServiceId().getName());
                    });
                });
        quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 10, 28);
        quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 16, 34);
    }
}
