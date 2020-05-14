package fr.yazhog.lionhub.player;

import fr.yazhog.lionapi.utils.Title;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.jump.CheckPoint;
import fr.yazhog.lionhub.utils.FormatTime;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class JumpPlayer {

    private static Map<Player, JumpPlayer> jumpPlayers = new HashMap<>();

    private Player player;
    private CheckPoint checkPoint;
    private int sec = 0;
    private Hub hub;
    private BukkitTask task;

    public JumpPlayer(Player player, CheckPoint checkPoint, Hub hub) {
        this.player = player;
        this.hub = hub;
        this.checkPoint = checkPoint;
        jumpPlayers.put(player, this);
    }

    public static JumpPlayer getInfos(Player player){
        return jumpPlayers.get(player);
    }

    public void start(){
        task = Bukkit.getScheduler().runTaskTimer(hub, new Runnable() {
            @Override
            public void run() {
                sec++;
                new Title().sendActionBar(player, ChatColor.GREEN + new FormatTime(sec).toString());
            }
        }, 20, 20);
    }

    public int getSec() {
        return sec;
    }

    public void stop(){
        task.cancel();
    }

    public static void delete(Player player) {
        jumpPlayers.remove(player);
    }

    public Player getPlayer() {
        return player;
    }

    public CheckPoint getCheckPoint() {
        return checkPoint;
    }

    public JumpPlayer setCheckPoint(CheckPoint checkPoint) {
        this.checkPoint = checkPoint;
        return this;
    }
}
