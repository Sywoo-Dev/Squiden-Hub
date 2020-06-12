package fr.sywoo.hub.jump;

import fr.sywoo.hub.utils.Cuboid;
import org.bukkit.*;

public enum CheckPoint {

    START(new Cuboid(new Location(Bukkit.getWorld("world"), -112, 148, -55), new Location(Bukkit.getWorld("world"), -110, 151, -57)), -115.0F, 0.00F, "§lJump §l» §2Vous commencez le jump, bonne chance !"),
    CHECKPOINT1(new Cuboid(new Location(Bukkit.getWorld("world"), 100, 140, -60), new Location(Bukkit.getWorld("world"), 98, 142, -63)), -40.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 1 "),
    CHECKPOINT2(new Cuboid(new Location(Bukkit.getWorld("world"), -43, 142, 76), new Location(Bukkit.getWorld("world"), -45, 145, 78)), 90.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 2"),
    FIN(new Cuboid(new Location(Bukkit.getWorld("world"), -146, 139, 15), new Location(Bukkit.getWorld("world"), -150, 145, 10)), -90.0F, 0.0F, "§lJump §l» §2Vous avez fini le jump en <time>. Bien joué !");

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
