package fr.yazhog.lionhub.utils;

import java.util.ArrayList;
import java.util.List;

public class HologramsList {

    private List<Holograms> holograms = new ArrayList<>();

    public HologramsList init(){
        holograms.add(new Holograms(new Location(-12, 17, 15).getAsLocation(), "§bEntrée du Saloon"));
        holograms.add(new Holograms(new Location(4, 15, 7).getAsLocation(), "§fBienvenue sur §6LionUHC", "§r", "§fDe nombreux §cjeux §fsont §edisponible",
                "§fComme l'§6UHC §fou l'§cArena !", "§r", "§c§l⚠ §r§bLe serveur est encore en §cbêta", "§cMerci de report tout les bugs trouvés", "§r", "§eAmuses-toi bien !"));
        holograms.add(new Holograms(new Location(-81, 11, 73).getAsLocation(), "§cNesquik"));
        holograms.add(new Holograms(new Location(91, 19, -87).getAsLocation(), "§cTokayz_"));
        holograms.add(new Holograms(new Location(60, 17, 82).getAsLocation(), "§cLa Mine"));
        return this;
    }

    public List<Holograms> getHolograms() {
        return holograms;
    }
}
