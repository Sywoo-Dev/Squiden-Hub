package fr.yazhog.lionhub.gui.servers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitTask;

import com.google.common.base.Strings;

import fr.yazhog.lionapi.inventory.IQuickInventory;
import fr.yazhog.lionapi.inventory.QuickInventory;
import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionapi.queue.Queue;
import fr.yazhog.lionapi.serverdata.GameType;
import fr.yazhog.lionapi.serverdata.ServerStatus;
import fr.yazhog.lionapi.serverdata.ServersData;
import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionapi.utils.Title;
import fr.yazhog.lionhub.Hub;

public class ServerPlayerGui extends IQuickInventory {

    private Hub hub;
    private BukkitTask uhc;
    private static Map<UUID, String> playerStartedServer = new HashMap<>();

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
                .setLore("", "§bLancez votre propre host UHC afin de profiter un", ChatColor.AQUA + "maximum des customisations de §6Lion§cUHC !", "").toItemStack(), quickEvent -> {
            quickEvent.getPlayer().closeInventory();
            if(playerStartedServer.containsKey(quickEvent.getPlayer().getUniqueId())){
                quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
                quickEvent.getPlayer().sendMessage("§cTu as déjà un serveur en cours de lancement, tu ne peux pas en lancer un autre");
                return;
            }
           // if(LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).getRank().hasPermission("lionuhc.servers.start")){
//                Rank rank = LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).getRank();
//                if(rank.getLimitHost() <= -1){
//                	rank.setLimitHost(-1);
//                	LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(quickEvent.getPlayer().getUniqueId()).setRank(rank));
//                } else if(rank.getLimitHost() == 0) {
//                    quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
//                    quickEvent.getPlayer().sendMessage("§cTu n'as pas assez d'host pour lancé une partie §a(Achètes en sur /store)");
//                    return;
//                } 
                
//                else if(rank.getLimitHost() >= 1){
//                    rank.removeHost(1);
//                }
                String name = LionSpigot.get().getServerManager().createAndGetServerName("UHC");
                playerStartedServer.put(quickEvent.getPlayer().getUniqueId(), name);
                LionSpigot.get().getServerDataManager().create(new ServersData(quickEvent.getPlayer().getName(), name, ServerStatus.WAITING, GameType.UHC));
                uhc = Bukkit.getScheduler().runTaskTimer(hub, new Runnable() {
                    int i = 0;
                    @Override
                    public void run() {
                        i++;
                        new Title().sendActionBar(quickEvent.getPlayer(), "§cChargement du serveur : §3" + getProgressBar(i, 20, 40, '|', ChatColor.GREEN, ChatColor.GRAY));
                        if(i == 20){
                            playerStartedServer.remove(quickEvent.getPlayer().getUniqueId());
                            LionSpigot.get().getPlayerServerManager().sendPlayerToServer(quickEvent.getPlayer(), name);
                            LionSpigot.get().getQueues().put(name, new Queue(name));
                            uhc.cancel();
                        }
                    }
                }, 20, 20);
//            } else {
//                quickEvent.getPlayer().sendMessage(ChatColor.RED + "Il faut au moins avoir le grade Bronze pour pouvoir lancer un serveur à la demande");
//                quickEvent.getPlayer().playSound(quickEvent.getPlayer().getLocation(), Sound.ANVIL_BREAK, 10, 10);
//            }
        } ,11);
    }

    public String getProgressBar(int current, int max, int totalBars, char symbol, ChatColor completedColor,
                                 ChatColor notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);

        return Strings.repeat("" + completedColor + symbol, progressBars)
                + Strings.repeat("" + notCompletedColor + symbol, totalBars - progressBars);
    }

    public static Map<UUID, String> getPlayerStartedServer() {
        return playerStartedServer;
    }
}
