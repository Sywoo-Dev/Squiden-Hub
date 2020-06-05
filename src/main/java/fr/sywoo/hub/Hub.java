package fr.sywoo.hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.ChatManager;
import fr.sywoo.hub.animas.AnimClassicMeetup;
import fr.sywoo.hub.animas.AnimGolemRush;
import fr.sywoo.hub.animas.AnimHikaBrain;
import fr.sywoo.hub.animas.AnimKaptur;
import fr.sywoo.hub.animas.AnimLG;
import fr.sywoo.hub.animas.AnimSkyWars;
import fr.sywoo.hub.animas.AnimTaupe;
import fr.sywoo.hub.animas.AnimUHCRun;
import fr.sywoo.hub.classements.HikabrainClassements;
import fr.sywoo.hub.customes.HubChampiMeuh;
import fr.sywoo.hub.customes.HubCrystal;
import fr.sywoo.hub.customes.HubEnderman;
import fr.sywoo.hub.customes.HubSheep;
import fr.sywoo.hub.customes.HubSnowMan;
import fr.sywoo.hub.enums.Games;
import fr.sywoo.hub.scoreboard.ScoreboardManager;
import fr.sywoo.hub.task.QueueRunnable;
import fr.sywoo.hub.utils.Classement;
import fr.sywoo.hub.utils.EventManager;
import fr.sywoo.hub.utils.Location;
import fr.sywoo.hub.utils.PlayerUtils;

public class Hub extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Classement classement;
    private PlayerUtils playerUtils;
    
    public List<Integer> customs = new ArrayList<>();
    public static Hub instance;
    
    public ArrayList<Games> maintaining = new ArrayList<Games>();
    
    public Map<UUID, Integer> jumps = new HashMap<>();
    
    public HikabrainClassements hikabrainClassement;
    
	private ChatManager chat;
		
	public ChatManager getChat() {
		return chat;
	}
	
    @Override
    public void onEnable() {
    	instance = this;

        playerUtils = new PlayerUtils();
        new EventManager(this).register(Bukkit.getPluginManager());
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager(this);
        new WorldCreator("world").createWorld();
        
        this.chat =  new ChatManager(true, true);

        this.hikabrainClassement = new HikabrainClassements();
        
        Bukkit.getScheduler().runTaskTimer(this, new QueueRunnable(), 0, 10);
        new BukkitRunnable() {
			
			@Override
			public void run() {
				for(Player players : Bukkit.getOnlinePlayers()) {
					hikabrainClassement.display(players);
				}
			}
		}.runTaskTimer(this, 0, 60);
		
		new BukkitRunnable() {
			
			@Override
			public void run() {
				System.out.println("Classements Update !");
				hikabrainClassement = new HikabrainClassements();
			}
		}.runTaskTimer(this, 0, 72000);
		
        classement = new Classement(new Location(68, 20, -63).getAsLocation(), "§dClassement du jump", LionSpigot.get().getAccountManager().getJumpers());
        new Location(-86.5, 64, 38.5).getAsLocation().getBlock().setType(Material.DRAGON_EGG);
        
        // BARRIER : new AnimArena();
        new AnimHikaBrain();
        new AnimKaptur();
        
        new AnimClassicMeetup();
        new AnimLG();
        new AnimTaupe();
        
        new AnimUHCRun();
        new AnimSkyWars();
        //BARRIER : new AnimSlasher();
        new AnimGolemRush();
        
        new HubSnowMan();
        new HubChampiMeuh();
        new HubSheep();
        new HubEnderman();
        new HubCrystal();
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
