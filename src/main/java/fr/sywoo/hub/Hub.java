package fr.sywoo.hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.api.utils.ChatManager;
import fr.sywoo.hub.animas.AnimArenaClassic;
import fr.sywoo.hub.animas.AnimArenaKit;
import fr.sywoo.hub.animas.AnimClassicMeetup;
import fr.sywoo.hub.animas.AnimGolemRush;
import fr.sywoo.hub.animas.AnimHikaBrain;
import fr.sywoo.hub.animas.AnimKaptur;
import fr.sywoo.hub.animas.AnimLG;
import fr.sywoo.hub.animas.AnimSkyWars;
import fr.sywoo.hub.animas.AnimTaupe;
import fr.sywoo.hub.animas.AnimUHCRun;
import fr.sywoo.hub.classements.ArenaClassement;
import fr.sywoo.hub.classements.ArenaKitClassement;
import fr.sywoo.hub.classements.HikabrainClassements;
import fr.sywoo.hub.classements.SkywarsKills;
import fr.sywoo.hub.classements.SkywarsVictory;
import fr.sywoo.hub.customes.HubChampiMeuh;
import fr.sywoo.hub.customes.HubCrystal;
import fr.sywoo.hub.customes.HubEnderman;
import fr.sywoo.hub.customes.HubSheep;
import fr.sywoo.hub.customes.HubSnowMan;
import fr.sywoo.hub.enums.Games;
import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.scoreboard.ScoreboardManager;
import fr.sywoo.hub.utils.Classement;
import fr.sywoo.hub.utils.EventManager;
import fr.sywoo.hub.utils.PlayerUtils;

public class Hub extends JavaPlugin {

    private ScoreboardManager scoreboardManager;
    private ScheduledExecutorService executorMonoThread;
    private ScheduledExecutorService scheduledExecutorService;
    private Classement classement, classement2;
    private PlayerUtils playerUtils;
    public NightClub nightClub;
    
    public Location carpet, slab;
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

        new WorldCreator("world").createWorld();
        playerUtils = new PlayerUtils();
        nightClub = new NightClub();
        scheduledExecutorService = Executors.newScheduledThreadPool(16);
        executorMonoThread = Executors.newScheduledThreadPool(1);
        scoreboardManager = new ScoreboardManager(this);
        
		carpet = new Location(Bukkit.getWorld("world"), 37, 62, -19);
		carpet.getChunk().load(true);
		carpet.getBlock().setType(Material.AIR);

		slab = new Location(Bukkit.getWorld("world"), -88, 58, 0);
		slab.getChunk().load(true);
		slab.getBlock().setType(Material.AIR);
		
        new EventManager(this).register(Bukkit.getPluginManager());

        maintaining.add(Games.GOLEMRUSH);
        maintaining.add(Games.AGEOFEMPIRE);

        
        this.chat =  new ChatManager(true, true);

        this.hikabrainClassement = new HikabrainClassements();
        
        new ArenaClassement();
        new ArenaKitClassement();
        new SkywarsVictory();
        new SkywarsKills();
        
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
		
        classement = new Classement(new Location(Bukkit.getWorld("world"), -110, 150, -59), "§dClassement du jump", LionSpigot.get().getAccountManager().getJumpers());
        classement2 = new Classement(new Location(Bukkit.getWorld("world"), -143.5, 55, 13.5), "§dClassement du jump", LionSpigot.get().getAccountManager().getJumpers());
        new Location(Bukkit.getWorld("world"), -86.5, 64, 38.5).getBlock().setType(Material.DRAGON_EGG);
        
        // BARRIER : new AnimArena();
        new AnimHikaBrain();
        new AnimKaptur();
        
        new AnimClassicMeetup();
        new AnimLG();
        new AnimTaupe();
        
        new AnimUHCRun();
        new AnimSkyWars();
        
        new AnimArenaKit();
        new AnimArenaClassic();
        
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
    	for(World world : Bukkit.getWorlds()) {
    		for(Entity entity : world.getEntities()) {
    			if(!(entity instanceof Player)) {
    				entity.remove();
    			}
    		}
    	}
    }

    public PlayerUtils getPlayerUtils() {
        return playerUtils;
    }

    public Classement getClassement() {
        return classement;
    }
    
    public Classement getClassement2() {
        return classement2;
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

	public NightClub getNightClub() {
		return nightClub;
	}


}
