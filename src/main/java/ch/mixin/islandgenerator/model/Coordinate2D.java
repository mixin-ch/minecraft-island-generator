package ch.mixin.islandgenerator.model;

import java.util.ArrayList;
import java.util.Objects;

public class Coordinate2D {
    private int x;
    private int z;

    public Coordinate2D(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Coordinate2D sum(Coordinate2D coordinate2D) {
        return sum(coordinate2D.getX(), coordinate2D.getZ());
    }

    public Coordinate2D sum(int x, int z) {
        return new Coordinate2D(this.x + x, this.z + z);
    }

    public Coordinate2D difference(Coordinate2D coordinate2D) {
        return difference(coordinate2D.getX(), coordinate2D.getZ());
    }

    public Coordinate2D difference(int x, int z) {
        return new Coordinate2D(this.x - x, this.z - z);
    }

    public double length() {
        return Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
    }

    public Coordinate3D to3D(int y) {
        return new Coordinate3D(x, y, z);
    }

    public ArrayList<Coordinate2D> neighbours() {
        ArrayList<Coordinate2D> neighbours = new ArrayList<>();
        neighbours.add(sum(1, 0));
        neighbours.add(sum(-1, 0));
        neighbours.add(sum(0, 1));
        neighbours.add(sum(0, -1));
        return neighbours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate2D that = (Coordinate2D) o;
        return x == that.x &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, z);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
