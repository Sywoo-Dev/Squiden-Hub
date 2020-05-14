package fr.yazhog.lionhub.npc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import fr.yazhog.lionhub.Hub;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import net.minecraft.server.v1_8_R3.WorldServer;

public class KapturGame {

	public KapturGame(Player player) {
		MinecraftServer server = MinecraftServer.getServer();
		GameProfile gameprofil = new GameProfile(UUID.randomUUID(), "§b§lKaptur");
		
		String[] prop;
        prop = textures("DetrimTv");
        byte flags = (byte) 0xFF;
        
        gameprofil.getProperties().put("textures", new Property("textures",prop[0],prop[1]));
		WorldServer world = ((CraftWorld) Bukkit.getWorlds().get(0)).getHandle();
		EntityPlayer npc = new EntityPlayer(server, world, gameprofil, new PlayerInteractManager(world));
		npc.setLocation(-7.5D, 15D, 5.5D, 220F, 5F);
		npc.getDataWatcher().watch(10, flags);
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, npc));
        connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
        connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) ((250%360.)*256/360)));
        
        new BukkitRunnable() {
			
			@Override
			public void run() {
		        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));				
			}
		}.runTaskLater(Hub.instance, 5L);
        
	}
	
	private String[] textures(String name) {
        try {
        URL url_0 = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
        InputStreamReader reader_0 = new InputStreamReader(url_0.openStream());
     
        String uuid = new JsonParser().parse(reader_0).getAsJsonObject().get("id").getAsString();
        URL url_1 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
        InputStreamReader reader_1 = new InputStreamReader(url_1.openStream());
     
        JsonObject textureProperty = new JsonParser().parse(reader_1).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
        String texture = textureProperty.get("value").getAsString();
        String signature = textureProperty.get("signature").getAsString();
        return new String[]{texture,signature};
        }catch(IOException e) {
            e.printStackTrace();
            return null;
        }
	}
}
