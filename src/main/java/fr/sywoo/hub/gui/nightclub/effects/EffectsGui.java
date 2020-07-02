package fr.sywoo.hub.gui.nightclub.effects;

import org.bukkit.Material;
import org.bukkit.SkullType;

import fr.sywoo.api.inventory.IQuickInventory;
import fr.sywoo.api.inventory.QuickInventory;
import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.nightclub.NightClub;
import fr.sywoo.hub.nightclub.effects.Effect;
import fr.sywoo.hub.utils.SkullProfil;

public class EffectsGui extends IQuickInventory {

	public NightClub nightClub;

	public EffectsGui(NightClub nightClub) {
		super("§6Effets", 1 * 9);
		this.nightClub = nightClub;
	}

	@Override
	public void contents(QuickInventory inv) {

		for (Effect effect : nightClub.getEffectS()) {
			inv.addItem(new SkullProfil(
					"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjIxZDkzZGE0Mzg2M2NiMzc1OWFmZWZhOWY3Y2M1YzgxZjM0ZDkyMGNhOTdiNzI4M2I0NjJmOGIxOTdmODEzIn19fQ==")
							.applyTextures(new QuickItem(Material.SKULL_ITEM,
									nightClub.enabledEffectS.contains(effect) ? 1 : -1, SkullType.PLAYER.ordinal())
											.setName("§6" + effect.getName()).setLore(nightClub.enabledEffectS.contains(effect) ? "§2Activé" : "§cDésactivé").toItemStack()),
					onClick -> {
						if (!nightClub.isEffectEnabled(effect)) {
							effect.start();
							onClick.getPlayer()
									.sendMessage("§6Discothèque :§c Vous avez activé l'effet : " + effect.getName());
						} else {
							effect.stop();
							onClick.getPlayer()
									.sendMessage("§6Discothèque :§c Vous avez désactivé l'effet : " + effect.getName());
						}
					});
		}

		inv.setItem(new SkullProfil(
				"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTY5NTkwNThjMGMwNWE0MTdmZDc1N2NiODViNDQxNWQ5NjZmMjczM2QyZTdjYTU0ZjdiYTg2OGUzMjQ5MDllMiJ9fX0=")
						.applyTextures(new QuickItem(Material.SKULL_ITEM, 1, SkullType.PLAYER.ordinal())
								.setName("§cTout désactiver").toItemStack()),
				onClick -> {
					for (Effect effect : nightClub.getEnabledEffectS()) {
						effect.stopSilent();
					}
					nightClub.getEnabledEffectS().clear();
					nightClub.checkProjectors();
					inv.close(onClick.getPlayer());
					onClick.getPlayer().sendMessage("§6Discothèque :§c Vous avez bien désactivé tous les effets !");
				}, 8);
	}

}
