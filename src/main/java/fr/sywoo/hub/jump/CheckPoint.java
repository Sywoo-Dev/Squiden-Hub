package fr.sywoo.hub.jump;

import org.bukkit.ChatColor;

import fr.sywoo.hub.utils.Cuboid;
import fr.sywoo.hub.utils.Location;

public enum CheckPoint {

    START(new Cuboid(new Location(-112, 148, -55).getAsLocation(), new Location(-110, 151, -57).getAsLocation()), -115.0F, 0.00F, "§lJump §l» §2Vous commencez le jump, bonne chance !"),
    CHECKPOINT1(new Cuboid(new Location(100, 140, -60).getAsLocation(), new Location(98, 142, -63).getAsLocation()), -40.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 1 "),
    CHECKPOINT2(new Cuboid(new Location(-43, 142, 76).getAsLocation(), new Location(-45, 145, 78).getAsLocation()), 90.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 2"),
    FIN(new Cuboid(new Location(-146, 139, 15).getAsLocation(), new Location(-150, 145, 10).getAsLocation()), -90.0F, 0.0F, "§lJump §l» §2Vous avez fini le jump en <time>. Bien joué !");

    private Cuboid cuboid;
    private Float yaw, pitch;
    private String message;

    CheckPoint(Cuboid cuboid, float yaw, float pitch, String message) {
        this.cuboid = cuboid;
        this.yaw = yaw;
        this.pitch = pitch;
        this.message = message;
    }

    public Cuboid getCuboid() {
        return cuboid;
    }

    public String getMessage() {
        return message;
    }

    public Float getPitch() {
        return pitch;
    }

    public Float getYaw() {
        return yaw;
    }
}
