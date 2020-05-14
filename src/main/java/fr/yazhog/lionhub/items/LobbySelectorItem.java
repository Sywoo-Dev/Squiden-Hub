package fr.yazhog.lionhub.items;

import org.bukkit.Material;

import fr.yazhog.lionapi.item.QuickItem;
import fr.yazhog.lionhub.Hub;
import fr.yazhog.lionhub.gui.servers.LobbyGui;

public class LobbySelectorItem extends QuickItem{

	public LobbySelectorItem() {
		super(Material.BEACON);
		this.setName("§a§lSélécteur de Lobby");
		this.setAction(action -> {
            new LobbyGui(Hub.instance).open(action.getPlayer());
        });
	}

}
