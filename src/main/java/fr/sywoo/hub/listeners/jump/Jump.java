package fr.sywoo.hub.listeners.jump;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.api.sanction.BanData;
import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.Hub;
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
        DecimalFormat decimalFormat = new DecimalFormat("#");
        Location location = new Location(player.getWorld(), player.getLocation().getX(), (player.getLocation().getY() - 1), player.getLocation().getZ());
        int x = Integer.parseInt(decimalFormat.format(player.getLocation().getX())),
                y = Integer.parseInt(decimalFormat.format(player.getLocation().getY())),
                z = Integer.parseInt(decimalFormat.format(player.getLocation().getZ()));
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
                    player.getInventory().setItem(0, new QuickItem(Material.SLIME_BALL).setName("§a§lRevenir au checkpoint").toItemStack());
                    player.getInventory().setItem(4, new QuickItem(Material.REDSTONE).setName("§c§lQuitter le jump").toItemStack());
                    return;
                } else if(checkPoint == CheckPoint.FIN){
                    if(JumpPlayer.getInfos(player) == null) return;
                    if(JumpPlayer.getInfos(player).getSec() <= 105){
                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.YEAR, 10);
                        date = calendar.getTime();
                        LionSpigot.get().getAccountManager().update(LionSpigot.get().getAccountManager().get(player.getUniqueId())
                                .setBanData(new BanData("Vous avez été banni car vous avez fini le jump trop rapidement, " +
                                        "donc pour suspections de cheat(s). Réclamation possible sur notre discord : https://discord.gg/jph5PhY=",
                                        date, "§c§l§nLe Plugin")));
                        player.kickPlayer("Vous avez été banni car vous avez fini le jump trop rapidement, " +
                                "donc pour suspections de cheat(s). Réclamation possible sur notre discord : https://discord.gg/jph5PhY=");
                        return;
                    }
                    JumpPlayer.getInfos(player).stop();
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
                    }
                    hub.getClassement().update(LionSpigot.get().getAccountManager().getJumpers());
                    for(Player players : Bukkit.getOnlinePlayers()){
                        hub.getClassement().getHolograms().display(players);
                    }
                    player.setWalkSpeed(0.3F);
                    player.sendMessage(checkPoint.getMessage().replace("<time>", new FormatTime(JumpPlayer.getInfos(player).getSec()).toString()));
                    player.sendMessage("§lJump §l» §2Téléportation au spawn dans 15 secondes....");
                    JumpPlayer.delete(player);
                    Bukkit.getScheduler().scheduleSyncDelayedTask(hub, new Runnable() {
                        @Override
                        public void run() {
                            player.teleport(new Location(player.getWorld(), 63.5, 16.0, -58.5, -90, 0));
                            player.getInventory().clear();
                            player.updateInventory();
                            player.getInventory().setItem(4,new QuickItem(Material.COMPASS).setName("§5Séléction des modes de jeux").setLore("§fChoisissez votre destination").toItemStack());
                            player.getInventory().setItem(8, new QuickItem(Material.BEACON).setName("§aSélécteur de lobby").toItemStack());
                            player.getInventory().setItem(0, new QuickItem(Material.GOLD_INGOT).setName("§eBoutique").toItemStack());
                            player.setGameMode(GameMode.ADVENTURE);
                            if (LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("lionuhc.lobby.fly")) {
                                player.setAllowFlight(true);
                                player.setFlying(true);
                            }
                        }
                    }, 20*15);
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
        if(location.getBlock().getType() == Material.GOLD_BLOCK) return;
        if((location.getBlock().getType() == Material.AIR)
                || (location.getBlock().getType() == Material.STAINED_GLASS)
                || (location.getBlock().getType() == Material.STEP)
                || (location.getBlock().getType() == Material.SLIME_BLOCK)
                || (location.getBlock().getType() == Material.SMOOTH_STAIRS) && JumpPlayer.getInfos(player) != null) {
            return;
        } else {
            if(JumpPlayer.getInfos(player) == null) return;
            CheckPoint checkPoint = JumpPlayer.getInfos(player).getCheckPoint();
            player.teleport(JumpPlayer.getInfos(player).getCheckPoint().getCuboid().getPCenter().setYaw(checkPoint.getYaw()).setPitch(checkPoint.getPitch()).getAsLocation());
        }
    }
}
