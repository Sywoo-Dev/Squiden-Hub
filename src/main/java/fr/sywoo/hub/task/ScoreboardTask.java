package fr.sywoo.hub.task;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.hub.Hub;

public class ScoreboardTask extends BukkitRunnable {

    private Hub hub;

    public ScoreboardTask(Hub hub) {
        this.hub = hub;
    }

    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            hub.getScoreboardManager().update(player);
        }
    }
}
