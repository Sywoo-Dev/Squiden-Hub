package fr.sywoo.hub.items;

import org.bukkit.Material;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.MainGui;

public class GameSelectorItem extends QuickItem{

	public GameSelectorItem() {
		super(Material.COMPASS);
		this.setName("§6§lSélecteur de Jeux");
		this.setLore("§fChoisissez votre destination");
		this.setAction(action -> {
            new MainGui(Hub.instance).open(action.getPlayer());
        });
	}

}
