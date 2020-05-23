package fr.sywoo.hub.gui.servers;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemFlag;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;

public class LobbyGui extends IQuickInventory {

    private Hub hub;

    public LobbyGui(Hub hub) {
        super("§aSélécteur de lobby", 9*5);
        this.hub = hub;
    }

    @Override
    public void contents(QuickInventory quickInventory) {
        quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 8);
        quickInventory.setHorizontalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 35, 44);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 0, 35);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).setName(" ").toItemStack(), 8, 44);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).toItemStack(), 10, 28);
        quickInventory.setVerticalLine(new QuickItem(Material.STAINED_GLASS_PANE).toItemStack(), 16, 34);
        quickInventory.updateItem("hub", taskUpdate -> {
            for(int i = 10; i < 16; i++){
                quickInventory.setItem(new QuickItem(Material.AIR).toItemStack(), i);
            }
            if(LionSpigot.get().getAccountManager().get(quickInventory.getOwner().getUniqueId()).getRank().hasPermission("*")){
                quickInventory.setItem(new QuickItem(Material.REDSTONE).setName("§cOuvre un Lobby").toItemStack(), quickEvent -> {
                    LionSpigot.get().getServerManager().createServer("Lobby");
                    quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.NOTE_PLING, 10, 10);
                    quickEvent.getPlayer().sendMessage("§aOuverture du serveur lobby.....");
                }, 8);
                CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
                        .filter(serviceInfoSnapshot -> serviceInfoSnapshot.getServiceId().getName().startsWith("Lobby-"))
                        .forEach(serviceInfoSnapshot -> {
                            String lobby = serviceInfoSnapshot.getServiceId().getName().substring(0, 5); String number = serviceInfoSnapshot.getServiceId().getName().substring(6);
                            String name = ChatColor.DARK_GRAY + lobby + " §b#" + number;
                            quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 5)
                                    .setName(name + " §7(" + hub.getPlayerUtils().getPlayers(serviceInfoSnapshot) + " joueurs)")
                                    .setLore("" , "§8⤷ §eClique ici pour rejoindre", "", "§4Drop l'item pour fermé le serveur en question")
                                    .addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 65)
                                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                                    .toItemStack(), quickEvent -> {
                                if(quickEvent.getClickType() == ClickType.DROP){
                                    LionSpigot.get().getServerManager().deleteServer(serviceInfoSnapshot.getServiceId().getName());
                                } else {
                                    quickEvent.getPlayer().closeInventory();
                                    quickEvent.getPlayer().sendMessage("§eConnexion à §a" + serviceInfoSnapshot.getServiceId().getName());
                                    quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 10);
                                    BridgePlayerManager.getInstance().proxySendPlayer(quickEvent.getPlayer().getUniqueId(), serviceInfoSnapshot.getServiceId().getName());
                                }
                            });
                        });
            } else {
                CloudNetDriver.getInstance().getCloudServiceProvider().getStartedCloudServices().stream()
                        .filter(serviceInfoSnapshot -> serviceInfoSnapshot.getServiceId().getName().startsWith("Lobby-"))
                        .forEach(serviceInfoSnapshot -> {
                            String lobby = serviceInfoSnapshot.getServiceId().getName().substring(0, 5);
                            String number = serviceInfoSnapshot.getServiceId().getName().substring(6);
                            String name = ChatColor.DARK_GRAY + lobby + " §b#" + number;
                            quickInventory.addItem(new QuickItem(Material.STAINED_CLAY, 1, (byte) 5)
                                    .setName(name + " §7(" + hub.getPlayerUtils().getPlayers(serviceInfoSnapshot) + " joueurs)")
                                    .setLore("", "§8⤷ §eClique ici pour rejoindre")
                                    .addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 65)
                                    .addItemFlag(ItemFlag.HIDE_ENCHANTS)
                                    .toItemStack(), quickEvent -> {
                                quickEvent.getPlayer().closeInventory();
                                quickEvent.getPlayer().sendMessage("§eConnexion à §a" + serviceInfoSnapshot.getServiceId().getName());
                                quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.LEVEL_UP, 10, 10);
                                BridgePlayerManager.getInstance().proxySendPlayer(quickEvent.getPlayer().getUniqueId(), serviceInfoSnapshot.getServiceId().getName());
                            });
                        });
            }
        }, 60);
        quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 10, 28);
        quickInventory.setVerticalLine(new QuickItem(Material.AIR).toItemStack(), 16, 34);
    }
}
