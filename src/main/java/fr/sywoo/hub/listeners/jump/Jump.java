package fr.sywoo.hub.listeners.jump;

import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.sywoo.api.sanction.BanData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.items.GameSelectorItem;
import fr.sywoo.hub.items.GuildItem;
import fr.sywoo.hub.items.JumpCheckpointItem;
import fr.sywoo.hub.items.JumpLeaveItem;
import fr.sywoo.hub.items.LobbySelectorItem;
import fr.sywoo.hub.items.ShopItem;
import fr.sywoo.hub.jump.CheckPoint;
import fr.sywoo.hub.player.JumpPlayer;
import fr.sywoo.hub.utils.FormatTime;

public class Jump implements Listener {

    private Hub hub;

    public Jump(Hub hub) {
        this.hub = hub;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(JumpPlayer.getInfos(player) != null) {
            player.setFlying(false);
            player.setAllowFlight(false);	
        }
        for(CheckPoint checkPoint : CheckPoint.values()){
            if(checkPoint.getCuboid().contains(player)){
                if(JumpPlayer.getInfos(player) == null && checkPoint == CheckPoint.START) {
                    new JumpPlayer(player, CheckPoint.START, hub);
                    JumpPlayer.getInfos(player).start();
                    player.sendMessage(checkPoint.getMessage());
                    player.setFlying(false);
                    player.setAllowFlight(false);
                    player.setWalkSpeed(0.2F);
                    player.setGameMode(GameMode.ADVENTURE);
                    player.getInventory().clear();
                    player.updateInventory();
                    player.getInventory().setItem(0, new JumpCheckpointItem().toItemStack());
                    player.getInventory().setItem(4, new JumpLeaveItem().toItemStack());
                    return;
                } else if(checkPoint == CheckPoint.FIN){
                    if(JumpPlayer.getInfos(player) == null) return;
                    if(JumpPlayer.getInfos(player).getSec() <= 105){
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.YEAR, 1);
                        date = calendar.getTime();
                        LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId())
                                .setBanData(new BanData("Vous avez été banni car vous avez fini le jump trop rapidement, " +
                                        "donc pour suspections de cheat(s). Réclamation possible sur notre discord : https://discord.gg/jph5PhY=",
                                        date, "§c§l§nLe Plugin")));
                        player.kickPlayer("Vous avez été banni car vous avez fini le jump trop rapidement, " +
                                "donc pour suspections de cheat(s). Réclamation possible sur notre discord : https://discord.gg/6fpXs8U");
                        return;
                    }
                    JumpPlayer.getInfos(player).stop();
                    boolean reward = false;
                    if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump() == null) {
                    	reward = true;
                    }
                    if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump() == null){
                    	fr.sywoo.api.jump.Jump jump = new fr.sywoo.api.jump.Jump(JumpPlayer.getInfos(player).getSec());
                        LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId()).setJump(jump));
                    } else {
                        if(LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump().getSec() > JumpPlayer.getInfos(player).getSec()
                                || LionSpigot.get().getAccountManager().get(player.getUniqueId()).getJump().getSec() == -1){
                            LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager()
                                    .get(player.getUniqueId()).setJump(new fr.sywoo.api.jump.Jump(JumpPlayer.getInfos(player).getSec())));
                        }
                    }
                    for(Player players : Bukkit.getOnlinePlayers()){
                        hub.getClassement().getHolograms().destroy(players);
                        hub.getClassement2().getHolograms().destroy(players);
                    }
                    hub.getClassement().update(LionSpigot.get().getAccountManager().getJumpers());
                    for(Player players : Bukkit.getOnlinePlayers()){
                        hub.getClassement().getHolograms().display(players);
                        hub.getClassement2().getHolograms().display(players);
                    }
                    player.setWalkSpeed(0.3F);
                    player.sendMessage(checkPoint.getMessage().replace("<time>", new FormatTime(JumpPlayer.getInfos(player).getSec()).toString()));
                    player.sendMessage("§lJump §l» §2Félicitations, vous avez terminé le jump !");
                    if(reward) {
                    	player.sendMessage("§aPour avoir complété le jump pour la première fois vous remporter 50 coins !");
                    	player.sendMessage("§6Vous avez également accès au banquet du roi Squid IV !");
                    	LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId()).addCoins(50));
                    }
                    JumpPlayer.delete(player);
                    player.getInventory().clear();
                    player.updateInventory();
            		player.getInventory().setItem(4, new GameSelectorItem().toItemStack());
            		player.getInventory().setItem(8, new LobbySelectorItem().toItemStack());
            		player.getInventory().setItem(0, new ShopItem().toItemStack());
            		player.getInventory().setItem(7, new GuildItem().toItemStack());
                    player.setGameMode(GameMode.ADVENTURE);
                    if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.lobby.fly")) {
                        player.setAllowFlight(true);
                        player.setFlying(true);
                    }
                    return;
                }
                if(JumpPlayer.getInfos(player) == null) return;
                JumpPlayer jumpPlayer = JumpPlayer.getInfos(player);
                if(checkPoint == jumpPlayer.getCheckPoint()) continue;
                player.sendMessage(checkPoint.getMessage());
                JumpPlayer.getInfos(player).setCheckPoint(checkPoint);
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 10);
            }
        }
        if(event.getTo().getY() < 130){
            if(JumpPlayer.getInfos(player) == null) return;
            CheckPoint checkPoint = JumpPlayer.getInfos(player).getCheckPoint();
            Location loc = JumpPlayer.getInfos(player).getCheckPoint().getCuboid().getPCenter();
            loc.setYaw(checkPoint.getYaw());
            loc.setPitch(checkPoint.getPitch());
            player.teleport(loc);
        }
    }
}
