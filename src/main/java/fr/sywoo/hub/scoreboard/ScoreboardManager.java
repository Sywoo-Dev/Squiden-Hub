package fr.sywoo.hub.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;

public class ScoreboardManager {
    private final Map<Player, PersonalScoreboard> scoreboards;
    private final ScheduledFuture<?> glowingTask;
    private final ScheduledFuture<?> reloadingTask;
    private int ipCharIndex;
    private int cooldown;

    public ScoreboardManager(Hub main) {
        scoreboards = new HashMap<>();
        ipCharIndex = 0;
        cooldown = 0;

        glowingTask = main.getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            String ip = colorIpAt();
            for (Map.Entry<Player, PersonalScoreboard> scoreboard : scoreboards.entrySet())
                main.getExecutorMonoThread().execute(() -> scoreboard.getValue().setLines(ip));
        }, 80, 80, TimeUnit.MILLISECONDS);

        reloadingTask = main.getScheduledExecutorService().scheduleAtFixedRate(() ->
        {
            for (PersonalScoreboard scoreboard : scoreboards.values())
                main.getExecutorMonoThread().execute(scoreboard::reloadData);
        }, 20, 20, TimeUnit.SECONDS);
    }

    public void onDisable() {
        scoreboards.values().forEach(PersonalScoreboard::onLogout);
    }
    
    public void onLogin(Player player) {
        if (scoreboards.containsKey(player)) {
            return;
        }
        scoreboards.put(player, new PersonalScoreboard(player));
    }

    public void onLogout(Player player) {
        if (scoreboards.containsKey(player)) {
            scoreboards.get(player).onLogout();
            scoreboards.remove(player);
        }
    }

    public void update(Player player) {
        if (scoreboards.containsKey(player)) {
            scoreboards.get(player).reloadData();
        }
    }

    private String colorIpAt() {
        String ip = LionSpigot.get().getProjectName();

        if (cooldown > 0) {
            cooldown--;
            return ChatColor.WHITE + ip;
        }

        StringBuilder formattedIp = new StringBuilder();

        if (ipCharIndex > 0) {
            formattedIp.append(ip.substring(0, ipCharIndex - 1));
            formattedIp.append(ChatColor.DARK_AQUA).append(ip.substring(ipCharIndex - 1, ipCharIndex));
        } else {
            formattedIp.append(ip.substring(0, ipCharIndex));
        }

        formattedIp.append(ChatColor.AQUA).append(ip.charAt(ipCharIndex));

        if (ipCharIndex + 1 < ip.length()) {
            formattedIp.append(ChatColor.DARK_AQUA).append(ip.charAt(ipCharIndex + 1));

            if (ipCharIndex + 2 < ip.length())
                formattedIp.append(ChatColor.WHITE).append(ip.substring(ipCharIndex + 2));

            ipCharIndex++;
        } else {
            ipCharIndex = 0;
            cooldown = 50;
        }

        return ChatColor.WHITE + formattedIp.toString();
    }

	public ScheduledFuture<?> getGlowingTask() {
		return glowingTask;
	}

	public ScheduledFuture<?> getReloadingTask() {
		return reloadingTask;
	}

}