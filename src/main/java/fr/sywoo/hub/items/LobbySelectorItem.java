package fr.sywoo.hub.items;

import org.bukkit.Material;

import fr.sywoo.api.item.QuickItem;
import fr.sywoo.hub.Hub;
import fr.sywoo.hub.gui.servers.LobbyGui;

public class LobbySelectorItem extends QuickItem{

	public LobbySelectorItem() {
		super(Material.BEACON);
		this.setName("§a§lSélécteur de Lobby");
		this.setAction(action -> {
            new LobbyGui(Hub.instance).open(action.getPlayer());
        });
	}

}
