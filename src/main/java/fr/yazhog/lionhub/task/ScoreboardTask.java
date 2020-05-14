package fr.yazhog.lionhub.task;

import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.utils.TabManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ScoreboardTask extends BukkitRunnable {

    private Hub hub;
    private TabManager tabManager;

    public ScoreboardTask(Hub hub) {
        this.hub = hub;
        tabManager = new TabManager();
    }

    @Override
    public void run() {
        tabManager.reloadTab();
        for(Player player : Bukkit.getOnlinePlayers()){
            hub.getScoreboardManager().update(player);
        }
    }
}
