package fr.sywoo.hub.jump;

import fr.sywoo.hub.utils.Cuboid;
import fr.sywoo.hub.utils.Location;
import org.bukkit.ChatColor;

public enum CheckPoint {

    START(new Cuboid(new Location(64, 15, -58).getAsLocation(), new Location(65, 17, -60).getAsLocation())
            , -90.00F, 0.00F, "§lJump §l» §2Vous commencez le jump, bonne chance !"),
    CHECKPOINT1(new Cuboid(new Location(77, 22, -14).getAsLocation(), new Location(75, 25, -12).getAsLocation()),
            0.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 1 "),
    CHECKPOINT2(new Cuboid(new Location(55, 35, 9).getAsLocation(), new Location(53, 38, 11).getAsLocation()),
            90.00F, 0.00F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 2"),
    CHECKPOINT3(new Cuboid(new Location(69, 34, 64).getAsLocation(), new Location(67, 37, 66).getAsLocation()),
            45.0F, 0.0F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 3"),
    CHECKPOINT4(new Cuboid(new Location(-23, 27, 84).getAsLocation(), new Location(-25, 30, 86).getAsLocation()),
            90.0F, 0.0F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 4"),
    CHECKPOINT5(new Cuboid(new Location(-60, 30, 50).getAsLocation(), new Location(-56, 33, 53).getAsLocation()),
            -131.0F, 0.0F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 5"),
    CHECKPOINT6(new Cuboid(new Location(-13, 38, -26).getAsLocation(), new Location(-11, 41, -28).getAsLocation()),
            135.0F, 5.0F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 6"),
    CHECKPOINT7(new Cuboid(new Location(-27, 47, -74).getAsLocation(), new Location(-24, 50, -76).getAsLocation()),
            -51.0F, 6.0F, "§lJump §l» §2Vous êtes au checkpoint" + ChatColor.YELLOW + " 7"),
    FIN(new Cuboid(new Location(60, 28, -69).getAsLocation(), new Location(58, 25, -71).getAsLocation()),
            -90.0F, 0.0F, "§lJump §l» §2Vous avez fini le jump en <time>. Bien joué !");

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
