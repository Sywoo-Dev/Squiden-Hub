package fr.sywoo.hub.utils;

import org.bukkit.Bukkit;
import org.bukkit.World;

public class Location {

    private Double x, y, z;
    private World world;
    private float yaw = 0f, pitch = 0f;

    public Location(World world, double x, double y, double z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Location(String world, double x, double y, double z) {
        this(Bukkit.getWorld(world), x, y, z);
    }

    public Location(double x, double y, double z) {
        this(Bukkit.getWorld("world"), x, y, z);
    }

    public Location(World world, int x, int y, int z) {
        this(world, Double.valueOf(Integer.toString(x)), Double.valueOf(Integer.toString(y)), Double.valueOf(Integer.toString(z)));
    }

    public Location(String world, int x, int y, int z) {
        this(Bukkit.getWorld(world), Double.valueOf(Integer.toString(x)), Double.valueOf(Integer.toString(y)), Double.valueOf(Integer.toString(z)));
    }

    public Location(int x, int y, int z) {
        this(Bukkit.getWorld("world"), Double.valueOf(Integer.toString(x)), Double.valueOf(Integer.toString(y)), Double.valueOf(Integer.toString(z)));
    }

    public Location setYaw(float yaw) {
        this.yaw = yaw;
        return this;
    }

    public Location setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getZ() {
        return z;
    }

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public World getWorld() {
        return world;
    }

    public org.bukkit.Location getAsLocation(){
        return new org.bukkit.Location(world, x, y, z, yaw, pitch);
    }

}
