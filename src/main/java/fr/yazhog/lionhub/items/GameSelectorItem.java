package fr.yazhog.lionhub.items;

import org.bukkit.Material;

import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.gui.MainGui;

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
