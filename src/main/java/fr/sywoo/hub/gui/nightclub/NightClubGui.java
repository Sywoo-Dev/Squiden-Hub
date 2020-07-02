package fr.sywoo.hub.gui.nightclub;

import org.bukkit.Material;
import org.bukkit.SkullType;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.gui.nightclub.effects.EffectsGui;
import fr.sywoo.hub.gui.nightclub.projectors.ProjectorsGui;
import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.nightclub.effects.Effect;
import fr.sywoo.hub.utils.SkullProfil;

public class NightClubGui extends IQuickInventory {

	public NightClub nightClub;

	public NightClubGui(NightClub nightClub) {
		super("§6Discothèque", 9 * 1);
		this.nightClub = nightClub;
	}

	@Override
	public void contents(QuickInventory inv) {
		inv.addItem(new SkullProfil(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGFmMzJjNjRiMTM4OTM3ZmNkNmMzMDNkNGFmYTg1ZGQ5YWQ2ZDgxZTQ0MzUyMjc0MWYzYmRlNzY3NGI2YjkyOCJ9fX0=")
						.applyTextures(new QuickItem(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal()).setName("§5Projecteurs").toItemStack()),
				onClick -> {
					new ProjectorsGui(nightClub).open(onClick.getPlayer());
				});
		inv.addItem(new SkullProfil(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjIxZDkzZGE0Mzg2M2NiMzc1OWFmZWZhOWY3Y2M1YzgxZjM0ZDkyMGNhOTdiNzI4M2I0NjJmOGIxOTdmODEzIn19fQ==")
						.applyTextures(new QuickItem(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal()).setName("§6Effets").toItemStack()),
				onClick -> {
					new EffectsGui(nightClub).open(onClick.getPlayer());
				});
		inv.setItem(new SkullProfil(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY5NTkwNThjMGMwNWE0MTdmZDc1N2NiODViNDQxNWQ5NjZmMjczM2QyZTdjYTU0ZjdiYTg2OGUzMjQ5MDllMiJ9fX0=")
				.applyTextures(new QuickItem(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal()).setName("§cTout désactiver").toItemStack()),
		onClick -> {
			for(Effect effect : nightClub.getEnabledEffects()) {
				effect.stopSilent();
			}
			nightClub.getEnabledEffects().clear();
			nightClub.checkProjectors();
			inv.close(onClick.getPlayer());
			onClick.getPlayer().sendMessage("§6Discothèque :§c Vous avez bien désactivé tous les effets !");
		}, 8);
	}

}
