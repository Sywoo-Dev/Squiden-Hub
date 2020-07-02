package fr.sywoo.hub.utils;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

public class SkullProfil {

	private final GameProfile gameProfile;

	public SkullProfil(String hash) {
		this.gameProfile = new GameProfile(UUID.randomUUID(), null);
		PropertyMap propertyMap = gameProfile.getProperties();
		propertyMap.put("textures", new Property("textures", hash));
	}

	public ItemStack applyTextures(ItemStack itemStack) {
		SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
		Class<?> c_skullMeta = skullMeta.getClass();
		try {
			Field f_profile = c_skullMeta.getDeclaredField("profile");
			f_profile.setAccessible(true);
			f_profile.set(skullMeta, gameProfile);
			f_profile.setAccessible(false);
			itemStack.setItemMeta(skullMeta);
			return itemStack;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return itemStack;
	}
}