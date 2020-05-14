package fr.yazhog.lionhub;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import fr.yazhog.lionapi.spigot.LionSpigot;
import fr.yazhog.lionapi.utils.ChatManager;
import fr.yazhog.lionhub.scoreboard.ScoreboardManager;
import fr.yazhog.lionhub.task.QueueRunnable;
import fr.yazhog.lionhub.utils.Classement;
import fr.yazhog.lionhub.utils.EventManager;
import fr.yazhog.lionhub.utils.HologramsList;
import fr.yazhog.lionhub.utils.Location;
import fr.yazhog.lionhub.utils.PlayerUtils;

public class Hub extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Classement classement;
    private PlayerUtils playerUtils;
    private HologramsList hologramsList;

    public static Hub instance;
    
	private ChatManager chat;
	
	public ChatManager getChat() {
		return chat;
	}
	
    @Override
    public void onEnable() {
    	instance = this;
        hologramsList = new HologramsList();
        hologramsList.init();
        playerUtils = new PlayerUtils();
        new EventManager(this).register(Bukkit.getPluginManager());
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager(this);
        new WorldCreator("world").createWorld();
        
        this.chat =  new ChatManager(true, true);

        Bukkit.getScheduler().runTaskTimer(this, new QueueRunnable(), 0, 10);
        classement = new Classement(new Location(68, 20, -63).getAsLocation(), "§dClassement du jump", LionSpigot.get().getAccountManager().getJumpers());
    }

    @Override
    public void onDisable() {
        stop();
    }

    private void stop() {

    }

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }

    public HologramsList getHologramsList() {
        return hologramsList;
    }

    public Classement getClassement() {
        return classement;
    }

    public ScheduledExecutorService getExecutorMonoThread() {
        return executorMonoThread;
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }


}
