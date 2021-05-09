package ch.mixin.islandgenerator.model;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate3D {
    private int x;
    private int y;
    private int z;

    public Coordinate3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Coordinate3D copy() {
        return new Coordinate3D(x, y, z);
    }

    public double length() {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2)+ Math.pow(z, 2), 0.5);
    }

    public Coordinate2D to2D() {
        return new Coordinate2D(x, z);
    }

    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public static Coordinate3D toCoordinate(Location location) {
        return new Coordinate3D(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public Coordinate3D sum(Coordinate3D c3d) {
        return sum(c3d.getX(), c3d.getY(), c3d.getZ());
    }

    public Coordinate3D sum(int x, int y, int z) {
        return new Coordinate3D(this.x + x, this.y + y, this.z + z);
    }

    public Coordinate3D stretch(int factor) {
        return new Coordinate3D(x * factor, y * factor, z * factor);
    }

    public Coordinate3D difference(Coordinate3D c3d) {
        return sum(-c3d.getX(), -c3d.getY(), -c3d.getZ());
    }

    public double distance(Coordinate3D c3d) {
        Coordinate3D dif = difference(c3d);
        return Math.pow(Math.pow(dif.getX(), 2) + Math.pow(dif.getY(), 2) + Math.pow(dif.getZ(), 2), 0.5);
    }

    public ArrayList<Coordinate3D> neighboursDirect2D() {
        ArrayList<Coordinate3D> neighbours = new ArrayList<>();
        neighbours.add(sum(1, 0, 0));
        neighbours.add(sum(-1, 0, 0));
        neighbours.add(sum(0, 0, 1));
        neighbours.add(sum(0, 0, -1));
        return neighbours;
    }

    public ArrayList<Coordinate3D> neighboursDirect() {
        ArrayList<Coordinate3D> neighbours = new ArrayList<>();
        neighbours.add(sum(1, 0, 0));
        neighbours.add(sum(-1, 0, 0));
        neighbours.add(sum(0, 1, 0));
        neighbours.add(sum(0, -1, 0));
        neighbours.add(sum(0, 0, 1));
        neighbours.add(sum(0, 0, -1));
        return neighbours;
    }

    public ArrayList<Coordinate3D> neighboursFull() {
        ArrayList<Coordinate3D> neighbours = new ArrayList<>();

        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    neighbours.add(sum(x, y, z));
                }
            }
        }

        neighbours.remove(this);
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate3D that = (Coordinate3D) o;
        return x == that.x && y == that.y && z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
