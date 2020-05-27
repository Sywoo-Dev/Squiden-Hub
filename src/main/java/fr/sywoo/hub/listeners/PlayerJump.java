package fr.sywoo.hub.listeners;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.util.Vector;

import fr.sywoo.api.spigot.LionSpigot;
import fr.sywoo.hub.player.JumpPlayer;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class PlayerJump implements Listener {

	@EventHandler
	public void onFlightAttempt(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		if(JumpPlayer.getInfos(player) != null) return;
		if(player.isFlying()) return;
		if(event.isFlying()) return;
		if(!LionSpigot.get().getAccountManager().get(player.getUniqueId()).getRank().hasPermission("hub.doublejump")) return;
		playEffect(player, EnumParticle.CLOUD, player.getLocation());
		player.playSound(player.getLocation(), Sound.IRONGOLEM_THROW, 1, 1);
		event.setCancelled(true);
		Vector v = player.getLocation().getDirection().multiply(1).setY(0.7);
		player.setVelocity(v);

	}

	public static void playEffect(Player player,EnumParticle particle, Location loc) {
		PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particle, true,
				(float) loc.getX(), (float) loc.getY(), (float) loc.getZ(), 0, 0, 0, 0, 1);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}

}
