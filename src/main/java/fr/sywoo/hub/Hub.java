package fr.sywoo.hub;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.ChatManager;
import fr.sywoo.hub.animas.AnimArena;
import fr.sywoo.hub.enums.Games;
import fr.sywoo.hub.scoreboard.ScoreboardManager;
import fr.sywoo.hub.task.QueueRunnable;
import fr.sywoo.hub.utils.Classement;
import fr.sywoo.hub.utils.EventManager;
import fr.sywoo.hub.utils.HologramsList;
import fr.sywoo.hub.utils.Location;
import fr.sywoo.hub.utils.PlayerUtils;

public class Hub extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Classement classement;
    private PlayerUtils playerUtils;
    private HologramsList hologramsList;

    public static Hub instance;
    
    public ArrayList<Games> maintaining = new ArrayList<Games>();
    
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
        new AnimArena();
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
